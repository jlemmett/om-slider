(ns om-slider.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [cljs.core.async :refer [put! chan <!]]
            [clojure.data :as data]
            [clojure.string :as string]))

(enable-console-print!)

(def app-state
  (atom
    {}))

(defn handle-slider-change [e owner state]
  (let [value (.. e -target -value)]
    (om/set-state! owner :value value)))

(defn slider [app owner]

  (reify

    om/IInitState
    (init-state [_]
      {:value 0})

    om/IRenderState
    (render-state [this state]
      (dom/div nil
      (dom/input #js {:type "range" :min -100 :max 100 :step 0.1 :value (:value state)
                      :onInput #(handle-slider-change % owner state)
                      :onChange #(handle-slider-change % owner state)
                      :className "slider"} nil)

      (dom/span nil (:value state))))


    )
  )


(om/root
  slider
  app-state
  {:target (. js/document (getElementById "slider"))})