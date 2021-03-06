package org.modeldriven.fuml.repository;

import java.util.List;



public interface Classifier extends NamedElement {


 
	public fuml.syntax.classification.Classifier getDelegate();
    
    public List<Classifier> getGeneral();
    
    public List<Classifier> getGeneralization();
    
    public Package getPackage();
    
    public boolean isAbstract();
    
    public boolean isDataType();
	
} // Classifier
