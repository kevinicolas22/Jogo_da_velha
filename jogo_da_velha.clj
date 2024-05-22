i(defn iniciar-jogo []
  {:tabuleiro (vec (repeat 9 " ")) :jogador-atual "X" :vitorias {"X" 0 "O" 0} :empates 0})

(defn imprimir-tabuleiro [estado]
  (let [tabuleiro (:tabuleiro estado)]
    (doseq [i (range 0 9 3)]
      (println (str (tabuleiro i) " | " (tabuleiro (+ i 1)) " | " (tabuleiro (+ i 2))))
      (when (< i 6) (println "---------")))))

(defn verificar-vitoria [estado]
  (let [tabuleiro (:tabuleiro estado)
        linhas-vencedoras [(list 0 1 2) (list 3 4 5) (list 6 7 8) (list 0 3 6) (list 1 4 7) (list 2 5 8) (list 0 4 8) (list 2 4 6)]]
    (loop [linhas linhas-vencedoras]
      (when-let [linha (first linhas)]
        (if (and (= (tabuleiro (first linha)) (tabuleiro (second linha)) (tabuleiro (nth linha 2))) (not= (tabuleiro (first linha)) " "))
          (tabuleiro (first linha))
          (if (not (some #(= % " ") tabuleiro))
            "empate"
            (recur (rest linhas))))))))

(defn jogar [estado posicao]
  (let [novo-estado (assoc estado :tabuleiro (assoc (:tabuleiro estado) posicao (:jogador-atual estado)))
        resultado (verificar-vitoria novo-estado)]
    (cond
      (= resultado "empate") (update novo-estado :empates inc)
      (resultado) (update-in novo-estado [:vitorias resultado] inc)
      :else (assoc novo-estado :jogador-atual (if (= (:jogador-atual novo-estado) "X") "O" "X")))))

(defn jogo-da-velha []
  (loop [estado (iniciar-jogo)]
    (imprimir-tabuleiro estado)
    (let [posicao (read-string (read-line))
          novo-estado (jogar estado posicao)
          resultado (verificar-vitoria novo-estado)]
      (when resultado
        (println "Resultado: " resultado)
        (println "Vitórias: " (:vitorias novo-estado))
        (println "Empates: " (:empates novo-estado))
        (if (= (read-line) "s")
          (recur (iniciar-jogo))
          (println "Fim do jogo!"))))
    (recur novo-estado)))

(defn -main []
  (jogo-da-velha))