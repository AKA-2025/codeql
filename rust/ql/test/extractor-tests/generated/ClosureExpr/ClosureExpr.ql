// generated by codegen, do not edit
import codeql.rust.elements
import TestUtils

from
  ClosureExpr x, int getNumberOfAttrs, string hasBody, string hasClosureBinder, string hasParamList,
  string hasRetType
where
  toBeTested(x) and
  not x.isUnknown() and
  getNumberOfAttrs = x.getNumberOfAttrs() and
  (if x.hasBody() then hasBody = "yes" else hasBody = "no") and
  (if x.hasClosureBinder() then hasClosureBinder = "yes" else hasClosureBinder = "no") and
  (if x.hasParamList() then hasParamList = "yes" else hasParamList = "no") and
  if x.hasRetType() then hasRetType = "yes" else hasRetType = "no"
select x, "getNumberOfAttrs:", getNumberOfAttrs, "hasBody:", hasBody, "hasClosureBinder:",
  hasClosureBinder, "hasParamList:", hasParamList, "hasRetType:", hasRetType
