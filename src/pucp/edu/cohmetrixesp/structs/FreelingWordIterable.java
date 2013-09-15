package pucp.edu.cohmetrixesp.structs;

import java.util.Iterator;

import edu.upc.freeling.ListWordIterator;
import edu.upc.freeling.Sentence;
import edu.upc.freeling.Word;

public class FreelingWordIterable implements Iterable<Word> {
	ListWordIterator lword;

	public FreelingWordIterable(Sentence sentence) {
		lword = new ListWordIterator(sentence);
	}

	@Override
	public Iterator<Word> iterator() {
		// TODO Auto-generated method stub
		return new Iterator<Word>() {
			@Override
			public boolean hasNext() {
				return lword.hasNext();
			}

			@Override
			public Word next() {
				return lword.next();
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

}
