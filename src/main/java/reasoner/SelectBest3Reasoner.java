package main.java.reasoner;

import net.sf.tweety.arg.dung.semantics.Extension;
import net.sf.tweety.arg.dung.syntax.WeightedDungTheory;

import java.util.*;


/**
 * reasoner for best_3 score
 *
 * find extensions where #beatenExtensions - #losses is maximal
 */
public class SelectBest3Reasoner extends AbstractSelectBestReasoner {
    /**
     * standard constructors
     */
    public SelectBest3Reasoner() {
        super();
    }
    public SelectBest3Reasoner(String aggrType) {
        super(aggrType);
    }

    @Override
    public Map<Extension, Double> getScores(WeightedDungTheory theory, Collection<Extension> exts) {
        Map<Extension, Double> extsScores = new HashMap<>();
        int maxScore = 0;
        for (Extension ext1 : exts) {
            int beaten = 0;
            int isBeaten = 0;
            for (Extension ext2: exts) {
                if (ext1.equals(ext2)) {
                    continue;
                }
                double score1 = this.compareExtensions(ext1, ext2, theory);
                double score2 = this.compareExtensions(ext2, ext1, theory);
                if (score1 > score2) {
                    beaten++;
                } else if (score2 > score1) {
                    isBeaten++;
                }
            }
            int score = beaten - isBeaten;
            extsScores.put(ext1, (double) score);
            if (score > maxScore) {
                maxScore = score;

            }
        }
        return extsScores;
    }
}
