// generated by codegen, do not edit
/**
 * This module provides the generated definition of `Impl`.
 * INTERNAL: Do not import directly.
 */

private import codeql.rust.elements.internal.generated.Synth
private import codeql.rust.elements.internal.generated.Raw
import codeql.rust.elements.AssocItemList
import codeql.rust.elements.Attr
import codeql.rust.elements.GenericParamList
import codeql.rust.elements.internal.ItemImpl::Impl as ItemImpl
import codeql.rust.elements.TypeRef
import codeql.rust.elements.Visibility
import codeql.rust.elements.WhereClause

/**
 * INTERNAL: This module contains the fully generated definition of `Impl` and should not
 * be referenced directly.
 */
module Generated {
  /**
   * A Impl. For example:
   * ```rust
   * todo!()
   * ```
   * INTERNAL: Do not reference the `Generated::Impl` class directly.
   * Use the subclass `Impl`, where the following predicates are available.
   */
  class Impl extends Synth::TImpl, ItemImpl::Item {
    override string getAPrimaryQlClass() { result = "Impl" }

    /**
     * Gets the assoc item list of this impl, if it exists.
     */
    AssocItemList getAssocItemList() {
      result =
        Synth::convertAssocItemListFromRaw(Synth::convertImplToRaw(this)
              .(Raw::Impl)
              .getAssocItemList())
    }

    /**
     * Holds if `getAssocItemList()` exists.
     */
    final predicate hasAssocItemList() { exists(this.getAssocItemList()) }

    /**
     * Gets the `index`th attr of this impl (0-based).
     */
    Attr getAttr(int index) {
      result = Synth::convertAttrFromRaw(Synth::convertImplToRaw(this).(Raw::Impl).getAttr(index))
    }

    /**
     * Gets any of the attrs of this impl.
     */
    final Attr getAnAttr() { result = this.getAttr(_) }

    /**
     * Gets the number of attrs of this impl.
     */
    final int getNumberOfAttrs() { result = count(int i | exists(this.getAttr(i))) }

    /**
     * Gets the generic parameter list of this impl, if it exists.
     */
    GenericParamList getGenericParamList() {
      result =
        Synth::convertGenericParamListFromRaw(Synth::convertImplToRaw(this)
              .(Raw::Impl)
              .getGenericParamList())
    }

    /**
     * Holds if `getGenericParamList()` exists.
     */
    final predicate hasGenericParamList() { exists(this.getGenericParamList()) }

    /**
     * Gets the self ty of this impl, if it exists.
     */
    TypeRef getSelfTy() {
      result = Synth::convertTypeRefFromRaw(Synth::convertImplToRaw(this).(Raw::Impl).getSelfTy())
    }

    /**
     * Holds if `getSelfTy()` exists.
     */
    final predicate hasSelfTy() { exists(this.getSelfTy()) }

    /**
     * Gets the trait of this impl, if it exists.
     */
    TypeRef getTrait() {
      result = Synth::convertTypeRefFromRaw(Synth::convertImplToRaw(this).(Raw::Impl).getTrait())
    }

    /**
     * Holds if `getTrait()` exists.
     */
    final predicate hasTrait() { exists(this.getTrait()) }

    /**
     * Gets the visibility of this impl, if it exists.
     */
    Visibility getVisibility() {
      result =
        Synth::convertVisibilityFromRaw(Synth::convertImplToRaw(this).(Raw::Impl).getVisibility())
    }

    /**
     * Holds if `getVisibility()` exists.
     */
    final predicate hasVisibility() { exists(this.getVisibility()) }

    /**
     * Gets the where clause of this impl, if it exists.
     */
    WhereClause getWhereClause() {
      result =
        Synth::convertWhereClauseFromRaw(Synth::convertImplToRaw(this).(Raw::Impl).getWhereClause())
    }

    /**
     * Holds if `getWhereClause()` exists.
     */
    final predicate hasWhereClause() { exists(this.getWhereClause()) }
  }
}
