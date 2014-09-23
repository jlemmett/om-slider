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


(defn handle-slider-change [e slider owner]
  (let [value (.. e -target -value)]
    (om/transact! slider :value (fn [_] value))))



(defcomponent slider [slider owner]

      (render [_]
              (print "render")
              (dom/div #js {:style #js {:background-color "lightgreen" :width "33%"
                                        :margin-top "25px" :margin-bottom: "25px"
                                        :border "solid black 1px"}} nil))

      (did-mount [state]

                (print "did mount")
                 (let [$slider-element ($ (.getDOMNode owner))
                       parameters #js {:start (:value slider)
                                                     :range #js {"max" #js [100] "min" #js [0]}
                                                     :step 10
                                                     :format (js/wNumb #js {:mark "," :decimals 1})}]


                   (print parameters)
                   (.noUiSlider $slider-element parameters)
                   (.on $slider-element #js {:slide (fn [] (print "Slide" " " (.val $slider-element)))})

                   )))


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
