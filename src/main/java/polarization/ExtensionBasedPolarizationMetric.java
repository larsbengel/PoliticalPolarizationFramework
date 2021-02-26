package main.java.polarization;

import net.sf.tweety.arg.dung.semantics.Extension;

import java.util.Collection;

/**
 * Interface for all polarization metric that compute polarization based on extensions
 */
public interface ExtensionBasedPolarizationMetric {

    /**
     * compute polarization between all given extensions
     * @param exts a set of extensions
     * @return the polarization between all given extensions
     */
    double getPolarization(Collection<Extension> exts);

    /**
     * compute the polarization between the given extensions
     * @param ext1 an extension
     * @param ext2 an extension
     * @return the computed polarization score
     */
    double getPolarization(Extension ext1, Extension ext2);
}
