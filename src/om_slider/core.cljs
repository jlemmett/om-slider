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
   {:sliders [{:value 0}
    {:value -25}
    {:value 50}]
    }))

(defn handle-slider-change [e owner state]
  (let [value (.. e -target -value)]

    (om/set-state! owner :value value)))

(defn slider [slider owner]

  (reify

    om/IRenderState
    (render-state [this state]
                  (dom/li nil
                          (dom/input #js {:type "range" :min -100 :max 100 :step 0.1 :value (:value slider)
                                          :onInput #(handle-slider-change % owner slider)
                                          :onChange #(handle-slider-change % owner slider)
                                          :className "slider"} nil)

                          (dom/span nil (:value slider))))


    )
  )


(defn sliders-view [app owner]
  (reify

    om/IRenderState
    (render-state [this state]
                  (dom/div nil
                           (dom/h2 nil "Sliders")
                           (apply dom/ul nil
                                  (om/build-all slider (:sliders app)))))))


(om/root
  sliders-view
  app-state
  {:target (. js/document (getElementById "slider"))})


(print @app-state)
