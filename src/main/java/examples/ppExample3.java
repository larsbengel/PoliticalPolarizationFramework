package main.java.examples;

import main.java.polarization.ExtensionBasedPolarizationMetric;
import main.java.polarization.JaccardIndex;
import main.java.semantics.WeightedSemantics;
import main.java.syntax.PoliticalPolarizationFramework;
import org.tweetyproject.arg.dung.reasoner.SimplePreferredReasoner;
import org.tweetyproject.arg.dung.semantics.Semantics;
import org.tweetyproject.arg.dung.syntax.Argument;
import org.tweetyproject.arg.dung.syntax.DungTheory;
import org.tweetyproject.arg.social.semantics.SimpleProductSemantics;
import org.tweetyproject.arg.social.syntax.SocialAbstractArgumentationFramework;

public class ppExample3 {
    public static void main(String[] args) {
        // create theory
        Argument a = new Argument("a_1");
        Argument b = new Argument("a_2");
        Argument c = new Argument("a_3");
        Argument d = new Argument("a_4");
        Argument e = new Argument("a_5");
        Argument f = new Argument("a_6");

        DungTheory dt = new DungTheory();
        dt.add(a);
        dt.add(b);
        dt.add(c);
        dt.add(d);
        dt.add(e);
        dt.add(f);

        dt.addAttack(a, d);
        dt.addAttack(a, e);
        dt.addAttack(a, f);
        dt.addAttack(b, d);
        dt.addAttack(b, e);
        dt.addAttack(b, f);
        dt.addAttack(c, d);
        dt.addAttack(c, e);
        dt.addAttack(c, f);
        dt.addAttack(d, a);
        dt.addAttack(d, b);
        dt.addAttack(d, c);
        dt.addAttack(e, a);
        dt.addAttack(e, b);
        dt.addAttack(e, c);
        dt.addAttack(f, a);
        dt.addAttack(f, b);
        dt.addAttack(f, c);


        // create social frameworks
        SocialAbstractArgumentationFramework saaf1 = new SocialAbstractArgumentationFramework();
        SocialAbstractArgumentationFramework saaf2 = new SocialAbstractArgumentationFramework();
        saaf1.add(dt);
        saaf2.add(dt);

        // votes for social framework 1
        saaf1.voteUp(a, 10);
        saaf1.voteDown(a, 20);
        saaf1.voteUp(b, 10);
        saaf1.voteDown(b, 20);
        saaf1.voteUp(c, 10);
        saaf1.voteDown(c, 20);
        saaf1.voteUp(d, 20);
        saaf1.voteDown(d, 10);
        saaf1.voteUp(e, 20);
        saaf1.voteDown(e, 10);
        saaf1.voteUp(f, 20);
        saaf1.voteDown(f, 10);


        System.out.println(new SimplePreferredReasoner().getModels(dt));
        // initialize political polarization framework
        SimpleProductSemantics socialSemantics = new SimpleProductSemantics(0.01, 0.001);
        Semantics semantics = Semantics.PREFERRED_SEMANTICS;
        WeightedSemantics weightedSemantics = WeightedSemantics.SELECT_BEST_3;
        ExtensionBasedPolarizationMetric polarizationMetric = new JaccardIndex();
        PoliticalPolarizationFramework ppf = new PoliticalPolarizationFramework(socialSemantics, semantics, weightedSemantics, polarizationMetric);

        System.out.println(ppf.computePolarization(saaf1));

    }
}
