// generated by codegen, do not edit
import codeql.rust.elements
import TestUtils

from ExternCrate x, int getNumberOfAttrs, string hasNameRef, string hasRename, string hasVisibility
where
  toBeTested(x) and
  not x.isUnknown() and
  getNumberOfAttrs = x.getNumberOfAttrs() and
  (if x.hasNameRef() then hasNameRef = "yes" else hasNameRef = "no") and
  (if x.hasRename() then hasRename = "yes" else hasRename = "no") and
  if x.hasVisibility() then hasVisibility = "yes" else hasVisibility = "no"
select x, "getNumberOfAttrs:", getNumberOfAttrs, "hasNameRef:", hasNameRef, "hasRename:", hasRename,
  "hasVisibility:", hasVisibility
