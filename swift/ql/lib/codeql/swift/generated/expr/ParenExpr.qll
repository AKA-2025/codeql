// generated by codegen/codegen.py
/**
 * This module provides the generated definition of `ParenExpr`.
 * INTERNAL: Do not import directly.
 */

private import codeql.swift.generated.Synth
private import codeql.swift.generated.Raw
import codeql.swift.elements.expr.IdentityExpr

module Generated {
  /**
   * INTERNAL: Do not reference the `Generated::ParenExpr` class directly.
   * Use the subclass `ParenExpr`, where the following predicates are available.
   */
  class ParenExpr extends Synth::TParenExpr, IdentityExpr {
    override string getAPrimaryQlClass() { result = "ParenExpr" }
  }
}
