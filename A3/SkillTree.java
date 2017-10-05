/**
 * Kristine Trinh
 * nlt895
 * 11190412
 */
import lib280.base.CursorPosition280;
import lib280.list.LinkedList280;
import lib280.tree.BasicMAryTree280;

public class SkillTree extends BasicMAryTree280<Skill> {

	/**	
	 * Create tree with the specified root node and 
	 * specified maximum arity of nodes.  
	 * @timing O(1) 
	 * @param x item to set as the root node
	 * @param m number of children allowed for future nodes 
	 */
	public SkillTree(Skill x, int m)
	{
		super(x,m);
	}

	/**
	 * A convenience method that avoids typecasts.
	 * Obtains a subtree of the root.
	 * 
	 * @param i Index of the desired subtree of the root.
	 * @return the i-th subtree of the root.
	 */
	public SkillTree rootSubTree(int i) {
		return (SkillTree)super.rootSubtree(i);
	}
	
	/**
	 * Creates a list which contains all the of the skills which are 
	 * prerequisites for obtaining the input skill including the input skill itself.
	 * 
	 * @param skillName desired skill name
	 * @return list which contains all dependencies of given skill
	 */
	public LinkedList280<Skill> skillDependencies(String skillName){
		LinkedList280<Skill> res = new LinkedList280<>();
		
		//perform recursive method
		this.traverseTree(skillName, res);
		
		//if skill has not been found
		if(res.isEmpty()){
			throw new RuntimeException("'" + skillName + "' skill does not exist");
		} else {
			return res;
		}
	}
	
	/**
	 * Calculates the total number of skill points that a player must invest to obtain 
	 * the given skill.
	 * 
	 * @param skillName desired skill name
	 * @return total cost of desired skill
	 */
	public int skillTotalCost(String skillName){
		
		//create a container
		LinkedList280<Skill> res = skillDependencies(skillName);
		
		// Save cursor position.
		CursorPosition280 p = res.currentPosition();
		
		int result = 0;
		
		// Iterate over all elements...
		res.goFirst();
		while( !res.after() ) {
			result += res.item().getSkillCost();
			res.goForth();
		}
		
		// Restore cursor
		res.goPosition(p);
		
		return result;
	}
	
	/**
	 * Private support recursive method to traverse the tree.
	 * 
	 * @param skillName desired skill name
	 * @param res result list
	 * @return result list (changed or not)
	 */
	private LinkedList280<Skill> traverseTree(String skillName, LinkedList280<Skill> res){
		
		/* break loop conditions */
		
		//desired name found
		if(this.rootItem().getSkillName().equals(skillName)){
			res.insert(this.rootItem());
			return res;
			
		//no more children 
		} else if(this.rootLastNonEmptyChild() == 0){
			return res;
		}
		
		//get the last child's index to perform a loop
		int lastChildIndex = this.rootLastNonEmptyChild();
		
		
		//go through children
		while(res.isEmpty() && lastChildIndex != 0){
			this.rootSubTree(lastChildIndex).traverseTree(skillName, res);
			lastChildIndex--;
		}
		
		//if no matches found
		if(!res.isEmpty()){
			res.insert(this.rootItem());
		}
		
		return res;
	}
	
	public static void main(String[] args) {
		
		/* constructing the tree */	
		SkillTree myTree = new SkillTree(new Skill("Fade mage", "", 1), 3);
		
		//subtrees
		SkillTree subTree1_0 = new SkillTree(new Skill("Fade Step", "", 3), 1);
		SkillTree subTree1_1 = new SkillTree(new Skill("Rift", "", 2), 1);
		SkillTree subTree1_2 = new SkillTree(new Skill("Shadow Barrier", "", 2), 3);
		
		SkillTree subTree2_0 = new SkillTree(new Skill("Blink", "", 4), 1);
		SkillTree subTree2_1 = new SkillTree(new Skill("Veil Burst", "", 3), 2);
		SkillTree subTree2_2 = new SkillTree(new Skill("Fire Storm", "", 2), 0);
		SkillTree subTree2_3 = new SkillTree(new Skill("Healing Touch", "", 2), 3);
		SkillTree subTree2_4 = new SkillTree(new Skill("Pull of the Abyss", "", 2), 0);
		
		SkillTree subTree3_0 = new SkillTree(new Skill("Thousand Cuts", "", 4), 0);
		SkillTree subTree3_1 = new SkillTree(new Skill("Concealing Fade", "", 4), 0);
		SkillTree subTree3_2 = new SkillTree(new Skill("Encircling Veil", "", 1), 0);
		SkillTree subTree3_3 = new SkillTree(new Skill("Fire Wall", "", 1), 0);
		SkillTree subTree3_4 = new SkillTree(new Skill("Flame", "", 1), 0);
		
		//construct all in one
		subTree2_1.setRootSubtree(subTree3_0, 1);
		subTree2_1.setRootSubtree(subTree3_4, 2);
		
		
		subTree2_3.setRootSubtree(subTree3_1, 1);
		subTree2_3.setRootSubtree(subTree3_2, 2);
		subTree2_3.setRootSubtree(subTree3_3, 3);
		
		subTree1_0.setRootSubtree(subTree2_0, 1);
		
		subTree1_1.setRootSubtree(subTree2_1, 1);
	
		subTree1_2.setRootSubtree(subTree2_2, 1);
		subTree1_2.setRootSubtree(subTree2_3, 2);
		subTree1_2.setRootSubtree(subTree2_4, 3);
		
		myTree.setRootSubtree(subTree1_0, 1);
		myTree.setRootSubtree(subTree1_1, 2);
		myTree.setRootSubtree(subTree1_2, 3);
		
		
		//display the tree
		System.out.println(myTree.toStringByLevel());

		System.out.println("Dependencies for Encircling Veil:\n" + myTree.skillDependencies("Encircling Veil"));
		System.out.println("Dependencies for Thousand Cuts:\n" + myTree.skillDependencies("Thousand Cuts"));
		System.out.println("Dependencies for Rift:\n" + myTree.skillDependencies("Rift"));
		
		try {
			System.out.println(myTree.skillDependencies("Void"));
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("To get Encircling Veil you need " + myTree.skillTotalCost("Encircling Veil") + " points");
		System.out.println("To get Thousand Cuts you need " + myTree.skillTotalCost("Thousand Cuts") + " points");
		System.out.println("To get Rift you need " + myTree.skillTotalCost("Rift") + " points");
		
		try {
			System.out.println(myTree.skillTotalCost("Fade bow"));
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		}
	}
	

}
