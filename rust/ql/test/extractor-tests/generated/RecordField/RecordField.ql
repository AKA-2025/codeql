// generated by codegen, do not edit
import codeql.rust.elements
import TestUtils

from RecordField x, int getNumberOfAttrs, string hasName, string hasTy, string hasVisibility
where
  toBeTested(x) and
  not x.isUnknown() and
  getNumberOfAttrs = x.getNumberOfAttrs() and
  (if x.hasName() then hasName = "yes" else hasName = "no") and
  (if x.hasTy() then hasTy = "yes" else hasTy = "no") and
  if x.hasVisibility() then hasVisibility = "yes" else hasVisibility = "no"
select x, "getNumberOfAttrs:", getNumberOfAttrs, "hasName:", hasName, "hasTy:", hasTy,
  "hasVisibility:", hasVisibility
