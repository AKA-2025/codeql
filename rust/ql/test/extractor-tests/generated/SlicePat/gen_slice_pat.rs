// generated by codegen, do not edit

fn test_slice_pat() -> () {
    // A slice pattern. For example:
    match x {
        [1, 2, 3, 4, 5] => "ok",
        [1, 2, ..] => "fail",
        [x, y, .., z, 7] => "fail",
    }
}
