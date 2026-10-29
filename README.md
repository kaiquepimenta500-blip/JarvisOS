# Jarvis OS — Etapa 1

App Android (Kotlin + Jetpack Compose + DataStore). Custo zero. minSdk 26, targetSdk 34.

## Status honesto (Etapa 1)
| Recurso | Estado |
|---|---|
| Interface Compose + chat local | Escrito, **não compilado ainda** |
| Identidade (nomes, saudação, mensagem inicial) | Escrito, não compilado |
| Tema escuro/claro/auto, cores #hex, raio, escala de texto, compacto | Escrito, não compilado |
| Persistência (DataStore) e restaurar padrões | Escrito, não compilado |
| Comandos locais: hora, data, nome, saudação | Escrito + 5 testes unitários, **não executados** |
| Ícone do app, papel de parede, logotipo, imagem de perfil | Pendente (ícone padrão vetorial incluído) |
| Exportar/importar backup | Pendente |
| Voz (STT/TTS), IA, memória, ações Android, automações, palavra-chave | Etapas 2–6, pendentes |

Nenhum APK foi gerado ou verificado ainda.

## Em tempo de execução vs. recompilação
- **Sem recompilar** (Configurações no app): nomes, saudações, tema, cores, tamanho, tom.
- **Exige recompilar**: nome na tela inicial (`res/values/strings.xml`), ícone (`res/drawable/ic_launcher.xml`), `applicationId`, dependências.

## Gerar o APK sem PC potente (GitHub Actions)
1. Crie conta gratuita no GitHub e um repositório (privado ou público).
2. Envie estes arquivos (site → *Add file → Upload files*, mantendo as pastas, incluindo `.github/workflows/build.yml`).
3. Aba **Actions** → *Build APK* → **Run workflow**.
4. Ao terminar, abra a execução e baixe o artefato **JarvisOS-debug-apk** (zip com `app-debug.apk`).
5. Extraia no celular, abra o APK e permita "instalar apps desconhecidos" para o navegador/arquivos.
6. Atualizações: faça backup (futuro: exportar config), gere novo APK e instale por cima (mesmo `applicationId`; APKs debug de máquinas diferentes podem ter assinaturas diferentes — nesse caso desinstale antes, o que apaga os dados).

Cotas: repositórios públicos têm minutos gratuitos de Actions; privados têm cota mensal limitada no plano gratuito. Confira os valores atuais no GitHub; não são ilimitados.

## Dependências (todas gratuitas, Apache 2.0)
AndroidX Core/Activity/Lifecycle, Jetpack Compose + Material3, DataStore Preferences, JUnit 4 (EPL 1.0, só testes).
