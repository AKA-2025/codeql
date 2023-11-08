import re

ts = TAINTED_STRING

pat = ... # some pattern
compiled_pat = re.compile(pat)

# see https://docs.python.org/3/library/re.html#functions
ensure_tainted(
    # returns Match object, see below
    re.search(pat, ts), # $ MISSING: tainted
    re.match(pat, ts), # $ MISSING: tainted
    re.fullmatch(pat, ts), # $ MISSING: tainted

    # other functions not returning Match objects
    re.split(pat, ts), # $ MISSING: tainted
    re.split(pat, ts)[0], # $ MISSING: tainted

    re.findall(pat, ts), # $ MISSING: tainted

    re.finditer(pat, ts), # $ MISSING: tainted
    [x for x in re.finditer(pat, ts)], # $ MISSING: tainted

    re.sub(pat, repl="safe", string=ts), # $ MISSING: tainted
    re.sub(pat, repl=lambda m: ..., string=ts), # $ MISSING: tainted
    re.sub(pat, repl=ts, string="safe"), # $ MISSING: tainted
    re.sub(pat, repl=lambda m: ts, string="safe"), # $ MISSING: tainted

    re.subn(pat, repl="safe", string=ts), # $ MISSING: tainted
    re.subn(pat, repl="safe", string=ts)[0], # $ MISSING: tainted // the string

    # same for compiled patterns
    compiled_pat.search(ts), # $ MISSING: tainted
    compiled_pat.match(ts), # $ MISSING: tainted
    compiled_pat.fullmatch(ts), # $ MISSING: tainted

    compiled_pat.split(ts), # $ MISSING: tainted
    compiled_pat.split(ts)[0], # $ MISSING: tainted

    # ...

    # user-controlled compiled pattern
    re.compile(ts), # $ tainted
    re.compile(ts).pattern, # $ MISSING: tainted
)

ensure_not_tainted(
    re.subn(pat, repl="safe", string=ts)[1], # // the number of substitutions made
)

# Match object
tainted_match = re.match(pat, ts)
safe_match = re.match(pat, "safe")
ensure_tainted(
    tainted_match.expand("Hello \1"), # $ MISSING: tainted
    safe_match.expand(ts), # $ MISSING: tainted
    tainted_match.group(), # $ MISSING: tainted
    tainted_match.group(1, 2), # $ MISSING: tainted
    tainted_match.group(1, 2)[0], # $ MISSING: tainted
    tainted_match[0], # $ MISSING: tainted

    tainted_match.groups(), # $ MISSING: tainted
    tainted_match.groups()[0], # $ MISSING: tainted
    tainted_match.groupdict(), # $ MISSING: tainted
    tainted_match.groupdict()["key"], # $ MISSING: tainted

    re.match(pat, ts).string, # $ MISSING: tainted
    re.match(ts, "safe").re, # $ MISSING: tainted
    re.match(ts, "safe").re.pattern, # $ MISSING: tainted

    compiled_pat.match(ts).string, # $ MISSING: tainted
    re.compile(ts).match("safe").re, # $ MISSING: tainted
    re.compile(ts).match("safe").re.pattern, # $ MISSING: tainted
)
ensure_not_tainted(
    safe_match.expand("Hello \1"),
    safe_match.group(),

    re.match(pat, "safe").re,
    re.match(pat, "safe").string,
)
