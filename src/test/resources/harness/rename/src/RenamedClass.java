import com.mpdeimos.chacy.Chacy;
import com.mpdeimos.chacy.Language;

@Chacy.Type
@Chacy.Name({
	@Chacy.Value("NewNameOfRenamedClass"),
	@Chacy.Value(lang=Language.CSHARP, value="CSharpNameOfRenamedClass")
})
public class RenamedClass
{
}
