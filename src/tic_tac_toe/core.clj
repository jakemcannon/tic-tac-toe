(ns tic-tac-toe.core
  (:gen-class))

(def game-state {:0 [["_" "_" "_"]]
                 :1 [["_" "_" "_"]]
                 :2 [["_" "_" "_"]]})

;; game checking logic
(defn three-in-row? [v]
  (or (= ((frequencies v) "X") 3) (= ((frequencies v) "O") 3)))

(defn row-win? [board]
  (or (three-in-row? (last (:0 board))) (three-in-row? (last (:1 board))) (three-in-row? (last (:2 board)))))

(defn col-win? [board]
  (or
   (three-in-row? (vec [(nth (last (:0 board)) 0) (nth (last (:1 board)) 0) (nth (last (:2 board)) 0)]))
   (three-in-row? (vec [(nth (last (:0 board)) 1) (nth (last (:1 board)) 1) (nth (last (:2 board)) 1)]))
   (three-in-row? (vec [(nth (last (:0 board)) 2) (nth (last (:1 board)) 2) (nth (last (:2 board)) 2)]))))

(defn diag-win? [board]
  (three-in-row?
   (vec
    [(nth (last (:0 board)) 0)
     (nth (last (:1 board)) 1)
     (nth (last (:2 board)) 2)])))

(defn reverse-diag-win? [board]
  (three-in-row?
   (vec
    [(nth (last (:2 board)) 2)
     (nth (last (:1 board)) 1)
     (nth (last (:0 board)) 0)])))

(defn game-won? [board]
  (or (row-win? board) (col-win? board) (diag-win? board) (reverse-diag-win? board)))

;; game checking logic

(defn print-board [board]
  (println (last (:0 board)))
  (println (last (:1 board)))
  (println (last (:2 board)))
  (println " "))

;; game loop logic
;; the user who goes first is always x
;; print the board
;; print line like the following
;; please input which row you want to select 0, 1 or 2 
;; please input which col you want to select 0, 1 or 2
;; validate that [row][col] that is available
;; update board


(defn int-to-keyword [int]
  (keyword (str int)))

(defn update-board [board [row col], round]
  (assoc-in board [(int-to-keyword row) (count ((int-to-keyword row) board))]
            (assoc (last ((int-to-keyword row) board)) col (if (= (mod round 2) 0) "O" "X"))))

;; last row of game-state uptated with new val
;; (update-board game-state [0 0] 0)
;; (update-board game-state [1 1] 0)
;; (update-board game-state [2 2] 0)


(defn handle-turn []
  (loop []
    (println "please select type 0, 1, or 2 to select your row and repeat for col")
    (let [row (Integer/parseInt (read-line)) col (Integer/parseInt (read-line))]
      (cond
        (or (< row 0) (> row 2)) (recur)
        (or (< col 0) (> col 2)) (recur)
        :else [row col]))))

(defn do-game []
  (loop [board game-state round 0]
    (print-board board)
    (if (game-won? board)
      (println "game won")
      (recur (update-board board (handle-turn) (+ round 1)) (+ round 1))))
  (println "game over"))

(do-game)



(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
