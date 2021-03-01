package main.java.examples;

import main.java.polarization.ExtensionBasedPolarizationMetric;
import main.java.polarization.JaccardIndex;
import main.java.semantics.WeightedSemantics;
import main.java.syntax.PoliticalPolarizationFramework;
import org.tweetyproject.arg.dung.semantics.Semantics;
import org.tweetyproject.arg.dung.syntax.Argument;
import org.tweetyproject.arg.dung.syntax.DungTheory;
import org.tweetyproject.arg.social.semantics.SimpleProductSemantics;
import org.tweetyproject.arg.social.syntax.SocialAbstractArgumentationFramework;

public class ex4 {
    public static void main(String[] args) {
        // create theory
        Argument a = new Argument("a_1");
        Argument b = new Argument("a_2");
        Argument c = new Argument("a_3");
        Argument d = new Argument("a_4");
        Argument e = new Argument("a_5");
        Argument f = new Argument("a_6");
        Argument g = new Argument("a_7");


        DungTheory dt = new DungTheory();
        dt.add(a);
        dt.add(b);
        dt.add(c);
        dt.add(d);
        dt.add(e);
        dt.add(f);
        //dt.add(g);

        dt.addAttack(a, b);
        dt.addAttack(b, a);
        dt.addAttack(a, c);
        dt.addAttack(e, c);
        dt.addAttack(e, f);
        dt.addAttack(f, e);
        dt.addAttack(d, b);

        // create social frameworks
        SocialAbstractArgumentationFramework saaf1 = new SocialAbstractArgumentationFramework();
        saaf1.add(dt);

        // votes for social framework 1
        saaf1.voteUp(a, 20);
        saaf1.voteDown(a, 20);
        saaf1.voteUp(b, 20);
        saaf1.voteDown(b, 20);
        saaf1.voteUp(c, 20);
        saaf1.voteDown(c, 20);
        saaf1.voteUp(d, 20);
        saaf1.voteDown(d, 20);
        saaf1.voteUp(e, 20);
        saaf1.voteDown(e, 20);
        saaf1.voteUp(e, 20);
        saaf1.voteDown(e, 20);



        // initialize political polarization framework
        SimpleProductSemantics socialSemantics = new SimpleProductSemantics(0.01, 0.001);
        Semantics semantics = Semantics.NAIVE_SEMANTICS;
        WeightedSemantics weightedSemantics = WeightedSemantics.BEST4;
        ExtensionBasedPolarizationMetric polarizationMetric = new JaccardIndex();
        PoliticalPolarizationFramework ppf = new PoliticalPolarizationFramework(socialSemantics, semantics, weightedSemantics, polarizationMetric);

        System.out.println(ppf.computePolarization(saaf1));

    }
}
