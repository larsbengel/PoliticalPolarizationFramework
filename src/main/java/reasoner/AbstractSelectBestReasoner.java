package main.java.reasoner;

import net.sf.tweety.arg.dung.semantics.Extension;
import net.sf.tweety.arg.dung.semantics.Semantics;
import net.sf.tweety.arg.dung.syntax.Argument;
import net.sf.tweety.arg.dung.syntax.WeightedDungTheory;

import java.util.*;

/**
 * Ancestor class for all Select Best Reasoners
 *
 * see: Coste-Marquis, Sylvie, et al. "Selecting extensions in weighted argumentation frameworks." COMMA 12 (2012)
 */
public abstract class AbstractSelectBestReasoner extends AbstractWeightedReasoner{
    /**
     * initialize reasoner with default aggregation type sum
     */
    public AbstractSelectBestReasoner() {
        super("sum");
    }

    /**
     * initialize reasoner with the given aggregation type
     * @param aggregationType a string representing an aggregation type eg. sum, max
     */
    public AbstractSelectBestReasoner(String aggregationType) {
        super(aggregationType);
    }

    /**
     * compute the scores for each extension
     * @param theory a dung theory
     * @param exts a set of extensions of the theory
     * @return a map with extensions and their scores
     */
    public abstract Map<Extension, Double> getScores(WeightedDungTheory theory, Collection<Extension> exts);

    @Override
    public Collection<Extension> getModels(WeightedDungTheory theory, Semantics semantics) {
        Collection<Extension> exts = this.getExtensions(theory, semantics);

        Map<Extension, Double> extsScores = this.getScores(theory, exts);

        double maxScore = extsScores.values().stream().mapToDouble(f -> f.doubleValue()).max().orElse(0);
        Collection<Extension> result = new HashSet<>();
        for (Extension ext: extsScores.keySet()) {
            if (extsScores.get(ext) == maxScore) {
                result.add(ext);
            }
        }
        return result;
    }

    @Override
    public Extension getModel(WeightedDungTheory theory, Semantics semantics) {
        return this.getModels(theory, semantics).iterator().next();
    }

    /**
     * measures the attacks from ext1 to ext2 wrt. the aggregation type
     * @param ext1 an extension
     * @param ext2 an extension
     * @param theory a dung theory
     * @param aggrFunction string representing an aggregation function eg. sum, max
     * @return a double representing the score of ext1 against ext2
     */
    public double compareExtensions(Extension ext1, Extension ext2, WeightedDungTheory theory, String aggrFunction) {
        List<Double> attacks = new ArrayList<>();
        for (Argument a: ext1) {
            for (Argument b: ext2) {
                if (theory.isAttackedBy(b, a)) {
                    attacks.add(theory.getWeight(a, b));
                }
            }
        }
        switch (aggrFunction) {
            case "max":
                return attacks.stream().mapToDouble(f -> f.doubleValue()).max().orElse(0);
            case "sum":
                return attacks.stream().mapToDouble(f -> f.doubleValue()).sum();
            default:
                return 0;
        }
    }

    /**
     * measures the attacks from ext1 to ext2 wrt. the sum aggregation
     * @param ext1 an extension
     * @param ext2 an extension
     * @param theory a dung theory
     * @return a double representing the score of ext1 against ext2
     */
    public double compareExtensions(Extension ext1, Extension ext2, WeightedDungTheory theory) {
        return this.compareExtensions(ext1, ext2, theory, "sum");

    }
}
