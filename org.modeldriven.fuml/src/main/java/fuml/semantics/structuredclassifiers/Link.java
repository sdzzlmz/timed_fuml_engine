
/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except  
 * as stated in the file entitled Licensing-Information. 
 * 
 * All modifications copyright 2009-2012 Data Access Technologies, Inc.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fuml.semantics.structuredclassifiers;

import fuml.Debug;
import fuml.semantics.simpleclassifiers.FeatureValue;
import fuml.semantics.simpleclassifiers.FeatureValueList;
import fuml.syntax.classification.ClassifierList;
import fuml.syntax.classification.Property;
import fuml.syntax.classification.PropertyList;

public class Link extends fuml.semantics.structuredclassifiers.ExtensionalValue {

	public fuml.syntax.structuredclassifiers.Association type = null;

	public void destroy() {
		// Remove the type of this link and destroy it.
		// Shift the positions of the feature values of any remaining links in
		// the extent of the same association, for ends that are ordered.

		Debug.println("[destroy] link = " + this.identifier);

		PropertyList ends = this.type.memberEnd;
		ExtensionalValueList extent = this.locus.getExtent(this.type);

		for (int i = 0; i < extent.size(); i++) {
			ExtensionalValue otherLink = extent.getValue(i);
			for (int j = 0; j < ends.size(); j++) {
				Property end = ends.getValue(j);
				if (end.multiplicityElement.isOrdered) {
					FeatureValue featureValue = otherLink.getFeatureValue(end);
					if (this.getFeatureValue(end).position < featureValue.position) {
						featureValue.position = featureValue.position - 1;
					}
				}
			}
		}

		this.type = null;
		super.destroy();
	} // destroy

	public fuml.semantics.values.Value copy() {
		// Create a new link with the same type, locus and feature values as
		// this link.

		Link newValue = (Link) (super.copy());

		newValue.type = this.type;

		return newValue;
	} // copy

	protected fuml.semantics.values.Value new_() {
		// Create a new link with no type or properies.

		return new Link();
	} // new_

	public fuml.syntax.classification.ClassifierList getTypes() {
		// Return the single type of this link (if any).

		ClassifierList types = null;

		if (this.type == null) {
			types = new ClassifierList();
		} else {
			types = new ClassifierList();
			types.addValue(this.type);
		}

		return types;

	} // getTypes

	public boolean isMatchingLink(
			fuml.semantics.structuredclassifiers.ExtensionalValue link,
			fuml.syntax.classification.Property end) {
		// Test whether the given link matches the values of this link on all
		// ends other than the given end.

		PropertyList ends = this.type.memberEnd;

		boolean matches = true;
		int i = 1;
		while (matches & i <= ends.size()) {
			Property otherEnd = ends.getValue(i - 1);
			if (otherEnd != end
					& !this.getFeatureValue(otherEnd).values.getValue(0)
							.equals(
									link.getFeatureValue(otherEnd).values
											.getValue(0))) {
				matches = false;
			}
			i = i + 1;
		}

		return matches;
	} // isMatchingLink

	public fuml.semantics.simpleclassifiers.FeatureValueList getOtherFeatureValues(
			fuml.semantics.structuredclassifiers.ExtensionalValueList extent,
			fuml.syntax.classification.Property end) {
		// Return all feature values for the given end of links in the given
		// extent whose other ends match this link.

		FeatureValueList featureValues = new FeatureValueList();
		for (int i = 0; i < extent.size(); i++) {
			ExtensionalValue link = extent.getValue(i);
			if (link != this) {
				if (isMatchingLink(link, end)) {
					featureValues.addValue(link.getFeatureValue(end));
				}
			}
		}
		return featureValues;
	} // getOtherFeatureValues

	public void addTo(fuml.semantics.loci.Locus locus) {
		// Add this link to the extent of its association at the given locus.
		// Shift the positions of ends of other links, as appropriate, for ends
		// that are ordered.

		Debug.println("[addTo] link = " + this.identifier);

		PropertyList ends = this.type.memberEnd;
		ExtensionalValueList extent = locus.getExtent(this.type);

		for (int i = 0; i < ends.size(); i++) {
			Property end = ends.getValue(i);
			if (end.multiplicityElement.isOrdered) {
				FeatureValue featureValue = this.getFeatureValue(end);
				FeatureValueList otherFeatureValues = this
						.getOtherFeatureValues(extent, end);
				int n = otherFeatureValues.size();
				if (featureValue.position < 0 | featureValue.position > n) {
					featureValue.position = n + 1;
				} else {
					if (featureValue.position == 0) {
						featureValue.position = 1;
					}
					for (int j = 0; j < otherFeatureValues.size(); j++) {
						FeatureValue otherFeatureValue = otherFeatureValues
								.getValue(j);
						if (featureValue.position <= otherFeatureValue.position) {
							otherFeatureValue.position = otherFeatureValue.position + 1;
						}
					}
				}
			}
		}

		locus.add(this);
	} // addTo

} // Link
