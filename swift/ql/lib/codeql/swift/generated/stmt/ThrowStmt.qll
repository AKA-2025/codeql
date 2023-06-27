// generated by codegen/codegen.py
private import codeql.swift.generated.Synth
private import codeql.swift.generated.Raw
import codeql.swift.elements.expr.Expr
import codeql.swift.elements.stmt.Stmt

module Generated {
  class ThrowStmt extends Synth::TThrowStmt, Stmt {
    override string getAPrimaryQlClass() { result = "ThrowStmt" }

    /**
     * Gets the sub expression of this throw statement.
     *
     * This includes nodes from the "hidden" AST. It can be overridden in subclasses to change the
     * behavior of both the `Immediate` and non-`Immediate` versions.
     */
    Expr getImmediateSubExpr() {
      result =
        Synth::convertExprFromRaw(Synth::convertThrowStmtToRaw(this).(Raw::ThrowStmt).getSubExpr())
    }

    /**
     * Gets the sub expression of this throw statement.
     */
    final Expr getSubExpr() {
      exists(Expr immediate |
        immediate = this.getImmediateSubExpr() and
        result = immediate.resolve()
      )
    }
  }
}
