package ontology;
import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.shared.PrefixMapping;

import java.util.*;

public class OntologyManager {

    private static ArrayList<OntClass> lastOnt=new ArrayList<OntClass>();
    
    public static void showClass(OntClass cls)
    {
        if(!cls.hasSubClass())
        {
                lastOnt.add(cls);
        }
        if (cls.canAs( OntClass.class )) {
            for (Iterator<OntClass> i = cls.listSubClasses( true );  i.hasNext(); ) {
                OntClass sub = i.next();                
                showClass( sub );
            }
        }
    }

    public static void printClass(OntClass cls)
    {
        PrefixMapping pm=cls.getModel();
        String uri=cls.getURI();
        System.out.print(pm.shortForm(uri).substring(pm.shortForm(uri).indexOf(':')+1));
    }
    
    public static void main( String[] args ) {
        OntModel m = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM, null );
        m.getDocumentManager().addAltEntry( "http://is.bupt.edu.cn/2012/luoyi/finalyearproject/Public_health_emergencies",
                                            "file:Public_health_emergencies.owl" );

        m.read( "http://is.bupt.edu.cn/2012/luoyi/finalyearproject/Public_health_emergencies" );
        Iterator<OntClass> i=m.listHierarchyRootClasses();
        
        while(i.hasNext())
        {
            OntClass c=i.next();
            showClass(c);//这个方法通过迭代遍历所有节点，找出所有满足hasSubClass()==false的节点，这些节点就是叶子节点，然后加入arrayList
        }

        for(OntClass c:lastOnt)
        {
            printClass(c);
            OntClass cc=c.getSuperClass();
            System.out.print(" ");
            printClass(cc);
            System.out.println();
        }

    }
}
