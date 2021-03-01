package main.java.reasoner;

import main.java.semantics.WeightedSemantics;
import org.tweetyproject.arg.dung.reasoner.AbstractExtensionReasoner;
import org.tweetyproject.arg.dung.semantics.*;
import org.tweetyproject.arg.dung.syntax.WeightedDungTheory;

import java.util.Collection;

/**
 * Ancestor class for all weighted extension-reasoner
 *
 */
public abstract class AbstractWeightedReasoner {
    protected String aggregationType;
    public AbstractWeightedReasoner(String aggregationType) {
        this.aggregationType = aggregationType;

    }

    /**
     * Creates a reasoner for the given semantics.
     * @param semantics a weighted semantics
     * @return a reasoner for the given semantics
     */
    public static AbstractWeightedReasoner getSimpleReasonerForSemantics(WeightedSemantics semantics){
        switch(semantics){
            case SMA: return new MostAttackingReasoner();
            case BEST1: return new SelectBest1Reasoner();
            case BEST2: return new SelectBest2Reasoner();
            case BEST3: return new SelectBest3Reasoner();
            case BEST4: return new SelectBest4Reasoner();
            default:
                throw new IllegalArgumentException("Unknown semantics.");
        }
    }

    public Collection<Extension> getExtensions(WeightedDungTheory theory, Semantics semantics) {
        // get extensions for the given semantics
        AbstractExtensionReasoner reasoner = AbstractExtensionReasoner.getSimpleReasonerForSemantics(semantics);
        Collection<Extension> exts = reasoner.getModels(theory);
        return exts;
    }

    /**
     *
     * @param theory a weighted argumentation framework
     * @param semantics a classical semantics
     * @return the extensions of the given theory wrt. the given semantics and the reasoner specifications
     */
    public abstract Collection<Extension> getModels(WeightedDungTheory theory, Semantics semantics);

    /**
     *
     * @param theory a weighted argumentation theory
     * @param semantics a classical semantics
     * @return one extension wrt. the given theory, semantics and reasoner specifications
     */
    public abstract Extension getModel(WeightedDungTheory theory, Semantics semantics);
}
