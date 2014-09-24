(ns om-slider.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [cljs.core.async :refer [put! chan <!]]
            [clojure.data :as data]
            [clojure.string :as string]
            [om-tools.core :refer-macros [defcomponent]]
            )

  (:use [jayq.core :only [$ css html]]))

(enable-console-print!)

(def app-state
  (atom
   {:sliders
    [
    {:id 1 :value 0}
    {:id 2 :value 75}
    {:id 3 :value 50}
     ]}))


(defn handle-slider-change [val slider owner]
    (om/transact! slider :value (fn [_] val)))



(defcomponent slider [slider owner]

      (render [_]
              (dom/div #js {:className "slider"
                            :style #js {
                                        :height "450px"
                                        :float "left"
                                        :margin-right "30px"
                                        :border "solid black 1px"}} nil))

      (did-mount [state]
                 (let [$slider-element ($ (.getDOMNode owner))
                       parameters #js {:start (:value slider)
                                       :range #js {"max" #js [100] "min" #js [0]}
                                       :step 1
                                       :format (js/wNumb #js {:mark "," :decimals 1})
                                       :direction "rtl"
                                       :orientation "vertical"}]


                   (.noUiSlider $slider-element parameters)
                   (.css $slider-element #js {:background-color "linear-gradient(red, #f06d06)"})
                   (.on $slider-element #js {:slide #(handle-slider-change (.val $slider-element) slider owner)}))))


(defcomponent sliders-view [app owner]
  (render-state [this state]
                (dom/div nil
                         (dom/h2 nil "Sliders")
                         (apply dom/div nil
                                (om/build-all slider (:sliders app)))
                         (dom/div #js {:style #js {:clear "left"}} nil))))

(defcomponent state-view [app owner]
    (render-state [this state]
                  (dom/div nil
                           (dom/pre nil (pr-str (:sliders app))))))


(om/root
  sliders-view
  app-state
  {:target (. js/document (getElementById "slider"))})

(om/root
  state-view
  app-state
  {:target (. js/document (getElementById "state"))})
