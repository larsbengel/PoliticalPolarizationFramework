package main.java.polarization;

import net.sf.tweety.arg.dung.semantics.Extension;
import net.sf.tweety.arg.dung.syntax.Argument;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public class TverskyIndex implements ExtensionBasedPolarizationMetric {
    private double alpha;
    private double beta;

    /**
     * initialize the tversky index with the given parameters
     * a=b=1 gives the jaccard index
     * a=b=0.5 gives the sorensen-dice index
     * @param alpha a value greater or equal than 0
     * @param beta a value greater or equal than 0
     */
    public TverskyIndex(double alpha, double beta) {
        this.alpha = alpha;
        this.beta = beta;
    }

    @Override
    public double getPolarization(Collection<Extension> exts) {
        if (exts.size() > 2) {
            throw new UnsupportedOperationException("Only works for 2 sets");
        }
         Iterator<Extension> iterator = exts.iterator();
        Extension ext1 = iterator.next();
        Extension ext2 = iterator.next();
        return this.getPolarization(ext1, ext2);
    }

    @Override
    public double getPolarization(Extension ext1, Extension ext2) {
        // get intersection
        Collection<Argument> intersection = new HashSet<>(ext1);
        intersection.retainAll(ext2);

        // get set diff 1
        Collection<Argument> diff1 = new HashSet<>(ext1);
        diff1.removeAll(ext2);

        // get set diff 2
        Collection<Argument> diff2 = new HashSet<>(ext2);
        diff2.removeAll(ext1);

        // calculate
        double result = intersection.size() / (intersection.size() + this.alpha*diff1.size() + this.beta*diff2.size());

        return 1 - result;
    }
}
