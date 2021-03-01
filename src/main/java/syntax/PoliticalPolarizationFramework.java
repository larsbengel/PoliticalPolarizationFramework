package main.java.syntax;

import main.java.polarization.ExtensionBasedPolarizationMetric;
import main.java.reasoner.*;
import main.java.semantics.WeightedSemantics;
import org.tweetyproject.arg.dung.reasoner.AbstractExtensionReasoner;
import org.tweetyproject.arg.dung.semantics.*;
import org.tweetyproject.arg.dung.syntax.*;
import org.tweetyproject.arg.social.reasoner.IssReasoner;
import org.tweetyproject.arg.social.semantics.*;
import org.tweetyproject.arg.social.syntax.SocialAbstractArgumentationFramework;

import java.util.*;

/**
 * A Framework for computing political polarization based on the extensions of social abstract argumentation frameworks
 */
public class PoliticalPolarizationFramework {
    /** reasoner for the social argumentation framework to compute the social support of each argument */
    IssReasoner socialReasoner;

    /** the semantics used to compute the eligible extensions */
    Semantics semantics;

    /** reasoner to compute the best extension for the weighted argumentation frameworks */
    AbstractWeightedReasoner weightedReasoner;

    /** metric for computing the political polarization from extensions*/
    ExtensionBasedPolarizationMetric polarizationMetric;

    /**
     * initialize a political polarization framework with the given parameters
     * @param socialSemantics a product semantics for social abstract argumentation
     * @param semantics a "classical" semantics
     * @param weightedSemantics a semantics for weighted argumentation frameworks
     * @param pMetric a political polarization metric which works on sets/extensions
     */
    public PoliticalPolarizationFramework(SimpleProductSemantics socialSemantics, Semantics semantics, WeightedSemantics weightedSemantics, ExtensionBasedPolarizationMetric pMetric) {
        this.socialReasoner = new IssReasoner(socialSemantics, 0.001);
        this.semantics = semantics;
        this.weightedReasoner = AbstractWeightedReasoner.getSimpleReasonerForSemantics(weightedSemantics);
        this.polarizationMetric = pMetric;

    }

    /**
     * compute polarization within the given social argumentation framework between 2 potential groups
     * @param saaf a social argumentation framework
     * @return the polarization score
     */
    public double computePolarization(SocialAbstractArgumentationFramework saaf) {
        return this.computePolarization(saaf, 2);
    }

    /**
     * compute polarization within the given social argumentation framework, following these steps:
     *
     * @param saaf a social argumentation framework
     * @param numGroups the number of political groups we suspect in the framework
     * @return the polarization score
     */
    public double computePolarization(SocialAbstractArgumentationFramework saaf, int numGroups) {
        // compute social support scores
        SocialMapping<Double> socialSupport = this.socialReasoner.getModel(saaf);

        // create weighted argumentation framework from given graph and social support scores as weights
        WeightedDungTheory waf = this.SAAFtoWAF(saaf, socialSupport);

        // get scores for all extensions wrt. the given semantics
        if (this.weightedReasoner instanceof AbstractSelectBestReasoner) {
            Map<Extension, Double> scores = ((AbstractSelectBestReasoner) this.weightedReasoner).getScores(waf, AbstractExtensionReasoner.getSimpleReasonerForSemantics(this.semantics).getModels(waf));

            // process extensions
            Map<Extension, Double> scores_sorted = new LinkedHashMap<>();
            scores.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .forEachOrdered(x -> scores_sorted.put(x.getKey(), x.getValue()));

            // get n best extensions
            Collection<Extension> bestExtensions = new HashSet<>();
            List<Extension> exts_sorted = new LinkedList<>(scores_sorted.keySet());
            for (int i = 0; i < numGroups; i++) {
                bestExtensions.add(exts_sorted.get(i));
            }

            // compute polarization
            return this.polarizationMetric.getPolarization(bestExtensions);
        } else {
            throw new IllegalArgumentException("Reasoner is not supported for this operation");
        }
    }

    /**
     * compute polarization between the two given social argumentation frameworks
     * @param saaf1 a social argumentation framework
     * @param saaf2 a social argumentation framework
     * @return the polarization score
     */
    public double computePolarization(SocialAbstractArgumentationFramework saaf1, SocialAbstractArgumentationFramework saaf2) {
        Collection<SocialAbstractArgumentationFramework> saafs = new ArrayList<>();
        saafs.add(saaf1);
        saafs.add(saaf2);
        return this.computePolarization(saafs);
    }

    /**
     * compute polarization between the given social argumentation frameworks, following these 5 steps:
     * 1. compute social support in each social argumentation framework
     * 2. convert to weighted argumentation framework
     * 3. compute "classical" extensions wrt. the given semantics
     * 4. select best extension wrt. the given weighted semantics
     * 5. compute polarization between the best extensions of each framework
     *
     * @param saafs a set of social argumentation frameworks
     * @return the polarization score
     */
    public double computePolarization(Collection<SocialAbstractArgumentationFramework> saafs) {
        // iterate over all given social argumentation frameworks
        Collection<Extension> bestExtensions = new ArrayList<>();
        for (SocialAbstractArgumentationFramework saaf: saafs) {
            // compute social support scores
            SocialMapping<Double> socialSupport = this.socialReasoner.getModel(saaf);


            // create weighted argumentation framework from given graph and social support scores as weights
            WeightedDungTheory waf = this.SAAFtoWAF(saaf, socialSupport);

            // get best extension of this WAF wrt. the given semantics
            Collection<Extension> extensions = this.weightedReasoner.getModels(waf, this.semantics);
            bestExtensions.add(extensions.iterator().next());
        }

        // compute polarization based on the best extensions of each framework
        return this.polarizationMetric.getPolarization(bestExtensions);
    }

    /**
     * convert a social argumentation framework to a weighted argumentation framework
     * i.e. use the social support of an argument as the weight of all its outgoing attacks
     * @param saaf a social argumentation framework
     * @param socialSupport the computed social support for this framework
     * @return the corresponding weighted argumentation framework
     */
    public WeightedDungTheory SAAFtoWAF(SocialAbstractArgumentationFramework saaf, SocialMapping<Double> socialSupport) {
        WeightedDungTheory waf = new WeightedDungTheory();
        waf.addAll(new HashSet<Argument>(saaf));
        for (Attack att: saaf.getAttacks()) {
            waf.addAttack(att.getAttacker(), att.getAttacked(), socialSupport.get(att.getAttacker()));
        }

        return waf;
    }
}

