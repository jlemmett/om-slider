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
    {:id 2 :value -25}
    {:id 3 :value 50}
     ]}))


(defn handle-slider-change [e slider owner]
  (let [value (.. e -target -value)]
    (om/transact! slider :value (fn [_] value))))



(defcomponent slider [slider owner]

      (render [_]

              (dom/div nil nil)

              )

      (did-mount [state]


                 (let [$slider-element ($ (.getDOMNode owner))
                       parameters #js {:start (:value slider)
                                                     :range #js {"min" #js [0] "max" #js [100]}
                                                     :step 10
                                                     :format (js/wNumb #js {:mark "," :decimals 1})}]



                   (.noUiSlider $slider-element parameters)))

;;     (render-state [this state]
;;                   (dom/li nil
;;                           (dom/input #js {:type "range" :min -100 :max 100 :step 0.1 :value (:value slider)
;;                                           :onInput #(handle-slider-change % slider owner)
;;                                           :onChange #(handle-slider-change % slider owner)
;;                                           :className "slider"} nil)

;;                           (dom/span nil (:value slider))))

  )


(defcomponent sliders-view [app owner]
  (render-state [this state]
                (dom/div nil
                         (dom/h2 nil "Sliders")
                         (apply dom/div nil
                                (om/build-all slider (:sliders app))))))

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









