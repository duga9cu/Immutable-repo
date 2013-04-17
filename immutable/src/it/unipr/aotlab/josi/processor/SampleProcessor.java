package it.unipr.aotlab.josi.processor;

import java.util.Set;

import javax.annotation.processing.*;
import javax.lang.model.element.*;
import javax.lang.model.util.ElementFilter;

/*
 * 
 * The {@code SampleProcessor} class implements a processor
 * that prints on console about elements accessible by an annotation
 * processor.
 * 
 * */

@SupportedAnnotationTypes("*")
public class SampleProcessor extends AbstractProcessor 
{
	@Override
	public boolean process(final Set<? extends TypeElement> a, final RoundEnvironment r)
	{
		for	(TypeElement e : ElementFilter.typesIn(r.getRootElements()))
		{
			System.out.println("qualified name " + e.getQualifiedName());
			
			for (AnnotationMirror m : e.getAnnotationMirrors())
			{
				System.out.println("annotation name " + m.getAnnotationType());
				System.out.println("annotation values " + m.getElementValues());
			}
			
			for (ExecutableElement i : ElementFilter.methodsIn(e.getEnclosedElements()))
			{
				System.out.println("method name " + i.getSimpleName());
				
				for (AnnotationMirror m : i.getAnnotationMirrors())
				{
					System.out.println("annotation name " + m.getAnnotationType());
					System.out.println("annotation values " + m.getElementValues());
					
				}
			}
		}
		
		return true;		
	}
}
