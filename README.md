# Trabalho de Teoria da Computação

## Dependências do Projeto

Observação: Não instalar o comando de forma global. (Conforme descrito na documentação do módulo)

Entre em algum diretório: 

`cd $DIRETORIO_CLI`

Instale a depência localmente: 

`npm install @mermaid-js/mermaid-cli`

### Criar um link para tornar o comando público:

`cd /usr/local/bin && sudo ln -s $DIRETORIO_CLI/node_modules/.bin/mmdc fluxo-teoria-computacao`

## Exemplo Execução:

`fluxo-teoria-computacao [options]`

### Exibir ajuda:

`fluxo-teoria-computacao -h`



# Exemplos

<pre>
1: faça F vá_para 2
2: se T1 então vá_para 1 senão vá_para 3
3: faça G vá_para 4
4: se T2 então vá_para 5 senão vá_para 1
</pre>

<pre>
1: faça A vá_para 3
2: se T1 então vá_para 4 senão vá_para 3
3: faça B vá_para 2
4: se T3 então vá_para 5 senão vá_para 1
</pre>

<pre>
1: faça A vá_para 3
2: se T1 então vá_para 1 senão vá_para 3
3: se T2 então vá_para 4 senão vá_para 2
</pre>

<pre>
1: faça A vá_para 3
2: faça C vá_para 4
3: faça B vá_para 2
4: se T3 então vá_para 5 senão vá_para 1
</pre>

<pre>
1: faça A vá_para 2
2: faça C vá_para 3
3: se T3 então vá_para 4 senão vá_para 3
</pre>

<pre>
1: se T1 então vá_para 3 senão vá_para 2
2: faça A vá_para 2
3: faça B vá_para 4
4: se T2 então vá_para 6 senão vá_para 5
5: faça C vá_para 8
6: se T3 então vá_para 5 senão vá_para 8
</pre>