package main.java.reasoner;

import net.sf.tweety.arg.dung.semantics.Extension;
import net.sf.tweety.arg.dung.semantics.Semantics;
import net.sf.tweety.arg.dung.syntax.WeightedDungTheory;

import java.util.*;

/**
 * reasoner for best_1 score
 *
 * find extension which is not beaten by any other extension
 */
public class SelectBest1Reasoner extends AbstractSelectBestReasoner {
    /**
     * standard constructors
     */
    public SelectBest1Reasoner() {
        super();
    }
    public SelectBest1Reasoner(String aggrType) {
        super(aggrType);
    }

    @Override
    public Map<Extension, Double> getScores(WeightedDungTheory theory, Collection<Extension> exts) {
        Map<Extension, Double> extsScore = new HashMap<>();
        for (Extension ext1 : exts) {
            boolean beaten = false;
            for (Extension ext2: exts) {
                if (ext1.equals(ext2)) {
                    continue;
                }
                double score1 = this.compareExtensions(ext1, ext2, theory);
                double score2 = this.compareExtensions(ext2, ext1, theory);
                if (score1 < score2) {
                    beaten = true;
                    break;
                }
            }
            if (!beaten) {
                extsScore.put(ext1, 1.0);
            } else {
                extsScore.put(ext1, 0.0);
            }
        }
        return extsScore;
    }

    @Override
    public Collection<Extension> getModels(WeightedDungTheory theory, Semantics semantics) {
        Collection<Extension> exts = this.getExtensions(theory, semantics);

        Map<Extension, Double> extsScores = this.getScores(theory, exts);

        double maxScore = extsScores.values().stream().mapToDouble(f -> f.doubleValue()).max().orElse(0);

        if (maxScore < 1.0) {
            return new HashSet<>();
        }

        Collection<Extension> result = new HashSet<>();
        for (Extension ext: extsScores.keySet()) {
            if (extsScores.get(ext) == maxScore) {
                result.add(ext);
            }
        }
        return result;
    }
}
