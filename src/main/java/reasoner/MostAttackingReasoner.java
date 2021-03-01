package main.java.reasoner;

import org.tweetyproject.arg.dung.reasoner.AbstractExtensionReasoner;
import org.tweetyproject.arg.dung.semantics.Extension;
import org.tweetyproject.arg.dung.semantics.Semantics;
import org.tweetyproject.arg.dung.syntax.Argument;
import org.tweetyproject.arg.dung.syntax.WeightedDungTheory;

import java.util.*;

/**
 * reasoner comparing sum of outgoing attacks of each extension and returning the extension with the highest value
 *
 */
public class MostAttackingReasoner extends AbstractWeightedReasoner {
    public MostAttackingReasoner() {
        super("sum");
    }

    @Override
    public Collection<Extension> getModels(WeightedDungTheory theory, Semantics semantics) {
        Collection<Extension> exts = new HashSet<>();
        exts.add(this.getModel(theory, semantics));

        return exts;
    }

    @Override
    public Extension getModel(WeightedDungTheory theory, Semantics semantics) {
        // get extensions for the given semantics
        AbstractExtensionReasoner reasoner = AbstractExtensionReasoner.getSimpleReasonerForSemantics(semantics);
        Collection<Extension> exts = reasoner.getModels(theory);
        Map<Extension, Double> extsUnsorted = new HashMap<>();
        for (Extension ext: exts) {
            // compute sum of outgoing attacks
            double score = 0;
            for (Argument a: ext) {
                for (Argument b: theory.getAttacked(a)) {
                    score += theory.getWeight(a, b);
                }
            }
            extsUnsorted.put(ext, score);
        }

        // sort map by score
        Map<Extension, Double> extsSorted = new LinkedHashMap<>();
        extsUnsorted.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> extsSorted.put(x.getKey(), x.getValue()));

        // best extension is first element of map
        return extsSorted.keySet().iterator().next();
    }
}
