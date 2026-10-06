package it.universita.esse3;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import it.universita.esse3.visibilita.Studente;

// Compila i sorgenti in src/test/resources/non-compilabili e restituisce i codici d'errore del compilatore
final class CompilatoreDiControesempi {
    private CompilatoreDiControesempi() {
    }

    static List<String> codiciErrore(String risorsa, String nomeCompletoClasse, Path cartellaOutput)
            throws IOException, URISyntaxException {
        String sorgente;
        try (InputStream in = CompilatoreDiControesempi.class.getResourceAsStream("/non-compilabili/" + risorsa)) {
            sorgente = new String(in.readAllBytes(), StandardCharsets.UTF_8);
        }
        Path classiProgetto = Path.of(Studente.class.getProtectionDomain().getCodeSource().getLocation().toURI());

        JavaCompiler compilatore = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostiche = new DiagnosticCollector<>();
        URI uri = URI.create("string:///" + nomeCompletoClasse.replace('.', '/') + ".java");
        JavaFileObject file = new SimpleJavaFileObject(uri, JavaFileObject.Kind.SOURCE) {
            @Override
            public CharSequence getCharContent(boolean ignoreEncodingErrors) {
                return sorgente;
            }
        };
        try (StandardJavaFileManager fileManager = compilatore.getStandardFileManager(diagnostiche, Locale.ENGLISH,
                StandardCharsets.UTF_8)) {
            List<String> opzioni = List.of("-classpath", classiProgetto.toString(), "-d", cartellaOutput.toString());
            compilatore.getTask(null, fileManager, diagnostiche, opzioni, null, List.of(file)).call();
        }
        return diagnostiche.getDiagnostics().stream()
                .filter(d -> d.getKind() == Diagnostic.Kind.ERROR)
                .map(Diagnostic::getCode)
                .toList();
    }
}
