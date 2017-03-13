package adt.skipList;

public class SkipListImpl<T> implements SkipList<T> {

	private static final int TWO = 2;
	private static final int ONE = 1;
	private static final int ZERO = 0;
	protected SkipListNode<T> root;
	protected SkipListNode<T> NIL;

	protected int maxHeight;

	protected double PROBABILITY = 0.5;

	public SkipListImpl(int maxHeight) {
		this.maxHeight = maxHeight;
		root = new SkipListNode(Integer.MIN_VALUE, maxHeight, null);
		NIL = new SkipListNode(Integer.MAX_VALUE, maxHeight, null);
		connectRootToNil();
	}

	/**
	 * Faz a ligacao inicial entre os apontadores forward do ROOT e o NIL Caso
	 * esteja-se usando o level do ROOT igual ao maxLevel esse metodo deve
	 * conectar todos os forward. Senao o ROOT eh inicializado com level=1 e o
	 * metodo deve conectar apenas o forward[0].
	 */
	private void connectRootToNil() {
		for (int i = ZERO; i < maxHeight; i++) {
			root.forward[i] = NIL;
		}
	}

	@Override
	public void insert(int key, T newValue, int height) {
		
		if (newValue == null || height > maxHeight || height < ZERO) {
			return;
		}
		
		SkipListNode<T>[] update =  new SkipListNode[this.maxHeight];
		SkipListNode<T> helper = this.root;
		
		for (int i = this.maxHeight - ONE; i >= ZERO; i--) {
			
			while (helper != null && helper.getForward(i) != null &&
					(!helper.equals(NIL)) && helper.getForward(i).getKey() < key) {
				helper = helper.getForward(i);
			}
			update[i] = helper;
		}
		
		if (helper.getForward(ZERO) != null) {
			helper = helper.getForward(ZERO);
		}
		
		if (helper.getKey() == key) {
			helper.setValue(newValue);
		} else {
			
			SkipListNode<T> newNode = new SkipListNode<>(key, height, newValue);
			for (int i = ZERO; i < height; i++) {
				newNode.forward[i] = update[i].forward[i];
				update[i].forward[i] = newNode;
			}			
		}
	}

	@Override
	public void remove(int key) {

		SkipListNode<T>[] update = new SkipListNode[this.maxHeight];
		SkipListNode<T> helper = this.root;
		int height = this.maxHeight;
		
		for (int i = height - ONE; i >= ZERO; i--) {
			
			while (helper.getForward(i).getKey() < key) {
				helper = helper.getForward(i);
			}
			
			update[i] = helper;
		}
		
		if (helper.getForward(ZERO) != null) {
			helper = helper.getForward(ZERO);
		}
		
		if (helper.getKey() == key) {
			
			for (int i = ZERO; i < this.height(); i++) {
				
				if (!update[i].getForward(i).equals(helper)) {
					break;
				}
				
				update[i].getForward()[i] = helper.getForward(i);
				
			}			
		}		
	}

	@Override
	public int height() {

		int height = ZERO;
		SkipListNode<T> forward = this.root.getForward(ZERO);

		while (!forward.equals(NIL)) {
			if (forward.height() > height) {
				height = forward.height();
			}
			forward = forward.getForward(ZERO);
		}
		return height;
	}

	@Override
	public SkipListNode<T> search(int key) {
		SkipListNode<T> helper = this.root;

		for (int i = this.maxHeight - 1; i >= ZERO; i--) {
			while (helper.forward[i] != null && (!helper.equals(NIL)) && helper.forward[i].key < key) {
				helper = helper.forward[i];
			}
		}

		if (helper.forward != null) {
			helper = helper.getForward(ZERO);
		}

		if (helper.key == key) {
			return helper;
		} else {
			return null;
		}
	}

	@Override
	public int size() {
		int size = ZERO;
		SkipListNode<T> helper = this.root.getForward(ZERO);

		while (helper.getForward(ZERO) != null) {
			size++;
			helper = helper.getForward(ZERO);
		}

		return size;
	}

	@Override
	public SkipListNode<T>[] toArray() {

		SkipListNode<T>[] array = new SkipListNode[size() + TWO];
		SkipListNode<T> helper = this.root;
		int index = ZERO;

		while (helper != null) {
			array[index++] = helper;
			helper = helper.getForward(ZERO);
		}

		return array;
	}

}
