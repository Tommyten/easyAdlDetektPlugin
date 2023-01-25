package es.horm.easyadldetektplugin.operations.rule

import es.horm.easyadldetektplugin.model.ExecutionScope
import es.horm.easyadldetektplugin.model.RuleEasyAdlOperation
import es.horm.easyadldetektplugin.model.StringArgument
import org.jetbrains.kotlin.psi.KtElement
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.psiUtil.collectDescendantsOfType
import org.jetbrains.kotlin.psi.psiUtil.isPublic

class MustDeclarePublicMethodOperation(private val methodName: StringArgument) : RuleEasyAdlOperation {

    companion object {

        private val spellings = listOf("must declare public method")

        fun matchesTokenText(tokenText: String): Boolean = spellings.any { it.equals(tokenText, true) }
    }

    override fun complies(ktElement: KtElement, executionScope: ExecutionScope): Boolean =
        ktElement.collectDescendantsOfType<KtNamedFunction>()
            .any { it.name?.equals(methodName.value, true) ?: false && it.isPublic }

    /*
    secondary identification: (element as KtCallExpression).getResolvedCall(bindingContext).resultingDescriptor.returnType.memberScope.getFunctionNames()
     */


    /*
    Identification of whether referenced class extends a given class with FqName:

    So bekommt man den package name vom File, in dem die Datei lebt:
    ((element as KtReferenceExpression).getResolvedCall(bindingContext).resultingDescriptor.containingDeclaration.toSourceElement.getPsi() as KtClass).collectDescendantsOfType<KtSuperTypeList>().first().entries.first().typeReference.containingKtFile.packageFqName

    Um den Namen der Klasse zu kriegen, die erweitert wird:
    ((element as KtReferenceExpression).getResolvedCall(bindingContext).resultingDescriptor.containingDeclaration.toSourceElement.getPsi() as KtClass).collectDescendantsOfType<KtSuperTypeList>().first().entries.first().typeAsUserType.referencedName

    Die kann man dan zusammenbappen und somit hat man alles was man braucht
     */
}
