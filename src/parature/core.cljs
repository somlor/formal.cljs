(ns parature.core
  (:use [parature.dom :only [attr by-id console-log data domready target]]))

(defn input-name [input]
  (attr input "name"))

(defn input-value [input]
  (.-value input))

(defn x-inputs [form]
  (let [inputs (.getElementsByTagName form "input")]
    (filter #(re-find #"^x_" (str (input-name %))) inputs)))

; map of input name/value pairs
(defn input-data [inputs]
  (let [names (map input-name inputs)
        values (map input-value inputs)]
    (zipmap names values)))

(defn on-submit [selector handler]
  (.addEventListener (by-id selector) "submit" handler))

(defn submit-handler [event]
  (console-log "submit handler")
  (.preventDefault event)

  (let [form (target event)
        form-id (data form "etrigue-form")
        inputs (x-inputs form)
        data (assoc (input-data inputs) "espformid" form-id)]

    (console-log (clj->js data))))

(domready
  (fn []
    (console-log "dom is ready")
    (on-submit "etrigue" submit-handler)))
