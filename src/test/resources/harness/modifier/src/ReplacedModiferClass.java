import com.mpdeimos.chacy.Chacy;
import com.mpdeimos.chacy.Language;
import com.mpdeimos.chacy.lang.CSharp;

@Chacy.Type
@Chacy.Modifier({@Chacy.Value({Chacy.Const.REMOVE+"abstract", "/* comment1 */",  "/* comment2 */"}), @Chacy.Value(lang=Language.CSHARP, value={Chacy.Const.REMOVE+"public", CSharp.MODIFIER_INTERNAL, CSharp.MODIFIER_SEALED})})
public abstract class ReplacedModiferClass
{
}
