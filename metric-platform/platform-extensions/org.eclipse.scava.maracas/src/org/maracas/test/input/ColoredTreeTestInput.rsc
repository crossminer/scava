module org::maracas::\test::input::ColoredTreeTestInput

@doc {
	ColoredTree Tests
	Taken from Rascal demo::common::CountConstructors
}
data ColoredTree 
	= leaf(int N)
	| red(ColoredTree left, ColoredTree right)
	| black(ColoredTree left, ColoredTree right);

public ColoredTree ct1 = black(black(leaf(1), red(leaf(2), red(leaf(3), leaf(4)))), red(leaf(5), leaf(6)));
public ColoredTree ct2 = leaf(1);
public ColoredTree ct3 = black(leaf(2), leaf(1));
public ColoredTree ct4 = red(black(red(leaf(1), leaf(2)), leaf(3)), black(red(leaf(4), leaf(5)), leaf(6)));
public ColoredTree ct5 = black(leaf(2), leaf(1));