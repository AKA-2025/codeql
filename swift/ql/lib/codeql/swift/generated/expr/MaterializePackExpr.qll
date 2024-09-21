// generated by codegen/codegen.py, do not edit
/**
 * This module provides the generated definition of `MaterializePackExpr`.
 * INTERNAL: Do not import directly.
 */

private import codeql.swift.generated.Synth
private import codeql.swift.generated.Raw
import codeql.swift.elements.expr.Expr
import codeql.swift.elements.expr.internal.ExprImpl::Impl as ExprImpl

/**
 * INTERNAL: This module contains the fully generated definition of `MaterializePackExpr` and should not
 * be referenced directly.
 */
module Generated {
  /**
   * An expression that materializes a pack during expansion. Appears around PackExpansionExpr.
   *
   * More details:
   * https://github.com/apple/swift-evolution/blob/main/proposals/0393-parameter-packs.md
   * INTERNAL: Do not reference the `Generated::MaterializePackExpr` class directly.
   * Use the subclass `MaterializePackExpr`, where the following predicates are available.
   */
  class MaterializePackExpr extends Synth::TMaterializePackExpr, ExprImpl::Expr {
    override string getAPrimaryQlClass() { result = "MaterializePackExpr" }

    /**
     * Gets the sub expression of this materialize pack expression.
     *
     * This includes nodes from the "hidden" AST. It can be overridden in subclasses to change the
     * behavior of both the `Immediate` and non-`Immediate` versions.
     */
    Expr getImmediateSubExpr() {
      result =
        Synth::convertExprFromRaw(Synth::convertMaterializePackExprToRaw(this)
              .(Raw::MaterializePackExpr)
              .getSubExpr())
    }

    /**
     * Gets the sub expression of this materialize pack expression.
     */
    final Expr getSubExpr() {
      exists(Expr immediate |
        immediate = this.getImmediateSubExpr() and
        if exists(this.getResolveStep()) then result = immediate else result = immediate.resolve()
      )
    }
  }
}
