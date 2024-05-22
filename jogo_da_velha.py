def iniciar_jogo():
    return {"tabuleiro": [" "]*9, "jogador_atual": "X", "vitorias": {"X": 0, "O": 0}, "empates": 0}

def imprimir_tabuleiro(estado):
    tabuleiro = estado["tabuleiro"]
    for i in range(0, 9, 3):
        print(f"{tabuleiro[i]} | {tabuleiro[i+1]} | {tabuleiro[i+2]}")
        if i < 6:
            print("---------")

def verificar_vitoria(estado):
    tabuleiro = estado["tabuleiro"]
    linhas_vencedoras = [(0,1,2), (3,4,5), (6,7,8), (0,3,6), (1,4,7), (2,5,8), (0,4,8), (2,4,6)]
    for linha in linhas_vencedoras:
        if tabuleiro[linha[0]] == tabuleiro[linha[1]] == tabuleiro[linha[2]] != " ":
            return tabuleiro[linha[0]]
    if " " not in tabuleiro:
        return "empate"
    return None

def jogar(estado, posicao):
    novo_estado = estado.copy()
    novo_estado["tabuleiro"] = list(novo_estado["tabuleiro"])
    novo_estado["tabuleiro"][posicao] = novo_estado["jogador_atual"]
    resultado = verificar_vitoria(novo_estado)
    if resultado == "empate":
        novo_estado["empates"] += 1
    elif resultado is not None:
        novo_estado["vitorias"][resultado] += 1
    else:
        novo_estado["jogador_atual"] = "O" if novo_estado["jogador_atual"] == "X" else "X"
    return novo_estado

def jogo_da_velha():
    estado = iniciar_jogo()
    while True:
        imprimir_tabuleiro(estado)
        posicao = int(input("Escolha uma posição (0-8): "))
        estado = jogar(estado, posicao)
        resultado = verificar_vitoria(estado)
        if resultado is not None:
            print(f"Resultado: {resultado}")
            print(f"Vitórias: {estado['vitorias']}")
            print(f"Empates: {estado['empates']}")
            if input("Jogar novamente? (s/n)") != "s":
                break
            estado = iniciar_jogo()

if __name__ == "__main__":
    jogo_da_velha()