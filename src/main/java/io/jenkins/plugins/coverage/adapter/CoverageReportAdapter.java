package io.jenkins.plugins.coverage.adapter;

import hudson.ExtensionPoint;
import hudson.model.Describable;
import hudson.model.Descriptor;
import io.jenkins.plugins.coverage.exception.CoverageException;
import io.jenkins.plugins.coverage.targets.CoverageResult;
import io.jenkins.plugins.coverage.threshold.Threshold;
import jenkins.model.Jenkins;
import org.kohsuke.stapler.DataBoundSetter;
import org.w3c.dom.Document;

import javax.annotation.CheckForNull;
import java.io.File;
import java.util.LinkedList;
import java.util.List;


public abstract class CoverageReportAdapter implements ExtensionPoint, Describable<CoverageReportAdapter> {

    // path of report file
    private final String path;
    private List<Threshold> thresholds = new LinkedList<>();

    /**
     * @param path report file Ant-style path
     */
    public CoverageReportAdapter(String path) {
        this.path = path;
    }


    /**
     * Get {@link CoverageResult} from source file.
     *
     * @param source source file
     * @return CoverageResult
     * @throws CoverageException cannot convert source file to CoverageResult
     */
    public CoverageResult getResult(File source) throws CoverageException {
        Document document = convert(source);
        return parseToResult(document, source.getName());
    }

    /**
     * convert report to standard format report, and return the DOM document representation.
     *
     * @param source report file
     * @return {@link Document} representation of standard format report
     */
    protected abstract Document convert(File source) throws CoverageException;

    /**
     * parse report document to {@link CoverageResult}.
     *
     * @param document   document be parse
     * @param reportName report name
     * @return CoverageResult
     */
    @CheckForNull
    protected abstract CoverageResult parseToResult(Document document, String reportName);


    @SuppressWarnings("unchecked")
    @Override
    public Descriptor<CoverageReportAdapter> getDescriptor() {
        return Jenkins.getInstance().getDescriptorOrDie(getClass());
    }


    /**
     * Getter for thresholds.
     *
     * @return thresholds
     * @see Threshold
     */
    public List<Threshold> getThresholds() {
        return thresholds;
    }

    /**
     * Setter for thresholds.
     *
     * @param thresholds value to set for thresholds
     */
    @DataBoundSetter
    public void setThresholds(List<Threshold> thresholds) {
        this.thresholds = thresholds;
    }

    /**
     * Getter for property 'path'.
     *
     * @return value for property 'path'
     */
    public String getPath() {
        return path;
    }
}