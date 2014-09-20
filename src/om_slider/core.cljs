(ns om-slider.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [cljs.core.async :refer [put! chan <!]]
            [clojure.data :as data]
            [clojure.string :as string])

  (:use [jayq.core :only [$ css html]])
  )

(enable-console-print!)

(def app-state
  (atom
   {:sliders
    [
    {:value 0}
    {:value -25}
    {:value 50}
     ]}))


(defn handle-slider-change [e slider owner]
  (let [value (.. e -target -value)]
    (om/transact! slider :value (fn [_] value))))

(defn slider [slider owner]

  (reify
    om/IRenderState
    (render-state [this state]
                  (dom/li nil
                          (dom/input #js {:type "range" :min -100 :max 100 :step 0.1 :value (:value slider)
                                          :onInput #(handle-slider-change % slider owner)
                                          :onChange #(handle-slider-change % slider owner)
                                          :className "slider"} nil)

                          (dom/span nil (:value slider))))))


(defn sliders-view [app owner]
  (reify

    om/IRenderState
    (render-state [this state]
                  (dom/div nil
                           (dom/h2 nil "Sliders")
                           (apply dom/ul nil
                                  (om/build-all slider (:sliders app)))))))

(defn state-view [app owner]
  (reify
    om/IRenderState
    (render-state [this state]
                  (dom/div nil
                           (dom/pre nil (pr-str (:sliders app)))))))


(om/root
  sliders-view
  app-state
  {:target (. js/document (getElementById "slider"))})

(om/root
  state-view
  app-state
  {:target (. js/document (getElementById "state"))})


(def $nouislider ($ :#nouislider))
(.noUiSlider $nouislider #js {:start [20, 80] :range #js {:min #js [0] :max #js [100]}})






