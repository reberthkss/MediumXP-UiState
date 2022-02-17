# Create: Criação de tarefas
A tela de criação de tarefas, é responsável por possibilitar a criação de tarefas. Ela possui dois campos- título e descrição, e um botão- Salvar que fica habilitado quando todos os campos estiverem preenchidos, e quando pressionado inicializa o salvamento dos dados.
Devemos notificar nossa UI de quando o salvamento está ocorrendo, fazemos isso através do estado de loading seguido de um estado de sucesso- que vai permitir o usuário navegar para a tela de detalhes da tarefa ou criar uma nova tarefa, ou erro- que mostra uma mensagem de erro e possibilita o usuário tentar salvar novamente.

# Read: Detalhes da tarefa
A tela de detalhes, é responsável por possibilitar a visualização detalhada da tarefa, que atualmente possui um titulo e descrição.
Ao acessar a tela, o carregamento dos dados se inicializa e colocamos a tela em estado de loading, seguido de um estado de sucesso- que vai mostrar os detalhes da tarefa e erro- que mostra uma mensagem de erro e um botão para tentar novamente.

# List: Lista de Tarefas
A tela de listagem, é responsável por carregar os dados referente as tarefas salvas pelo usuário. Ela possui apenas um recycler view que é responsável por renderizar cada tarefa salva.
Devemos indicar para o usuário que o carregamento de dados está ocorrendo através do estado de loading seguido de um estado de sucesso- que vai mostrar a lista de tarefas,ou erro- que vai mostrar uma mensagem de erro e um botão para tentar carregar os dados novamente.

# Update: Atualização da Tarefa
A tela de atualização, será acessível ao pressionar o ícone de edição na tela de listagem de tarefas ou na tela de detalhes.
Devemos indicar para o usuário que a atualização da tarefa está ocorrendo através do estado de loading seguido de um estado de sucesso- que vai permitir o usuário navegar para a tela inicial ou editar novamente a tarefa, e erro- que vai permitir o usuário tentar novamente ou voltar para o estado de edição.

# Delete: Remoção da tarefa
A tela de detalhe, será acessível ao pressionar o ícone de remoção que está na lista de tarefas ou no detalhe da tarefa.
Devemos indicar para o usuário que a remoção está ocorrendo através do estado de loading seguido de um estado de sucesso- que vai permitir o usuário navegar para a tela anterior, e erro- que vai permitir o usuário tentar novamente ou voltar para a tela anterior.