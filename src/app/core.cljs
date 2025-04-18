(ns app.core
  (:require
   ["@babylonjs/core/Cameras/freeCamera.js" :refer [FreeCamera]]
   ["@babylonjs/core/Engines/engine.js" :refer [Engine]]
   ["@babylonjs/core/Lights/hemisphericLight.js" :refer [HemisphericLight]]
   ["@babylonjs/core/Maths/math.vector.js" :refer [Vector3]]
   ["@babylonjs/core/Meshes/Builders/groundBuilder.js" :refer [CreateGround]]
   ["@babylonjs/core/Meshes/Builders/boxBuilder.js" :refer [CreateBox]]
   ["@babylonjs/core/scene.js" :refer [Scene]]
   ["@babylonjs/core/Physics/v2/Plugins/havokPlugin.js" :refer [HavokPlugin]]
   ["@babylonjs/core/Physics/v2/IPhysicsEnginePlugin.js" :refer [PhysicsShapeType]]
   ["@babylonjs/core/Physics/v2/physicsAggregate.js" :refer [PhysicsAggregate]]
   ["@babylonjs/core/Physics/physicsEngineComponent.js"]
   ["@babylonjs/materials/simple/simpleMaterial.js" :refer [SimpleMaterial]]
   ["@babylonjs/havok$default" :as HavokPhysics]))

(defn- prepare-container! [id]
  (let [container (js/document.getElementById id)]
    (set! (.-innerHTML container) "")
    container))

(defn app [havok-instance]
  (let [hk (new HavokPlugin true havok-instance);
        canvas (prepare-container! "render")
        engine  (new Engine canvas true #js {:stencil true
                                             :preserveDrawingBuffer true
                                             :disableWebGL2Support false})
        scene (new Scene engine)
        material (new SimpleMaterial "simple" scene);
        camera (new FreeCamera "camera1" (new Vector3 0 5 -10) scene)
        light (new HemisphericLight "light" (new Vector3 0 1 0) scene)
        box1 (CreateBox "box1" #js {:width 1 :height 1} scene)
        box2 (CreateBox "box1" #js {:width 1 :height 1} scene)
        ground (CreateGround "ground" #js {:width 10 :height 10})]

    (.setTarget camera (. Vector3 Zero))
    (.attachControl camera canvas true)
    (set! (.-material box1) material)
    (set! (.-material box2) material)
    (set! (.-material ground) material)
    (set! (.-intensity light) 0.7)
    (set! (-> box1 .-position .-y) 2.5)
    (set! (-> box2 .-position .-y) 5)
    (set! (-> box2 .-position .-x) 0.5)
    (.enablePhysics scene (new Vector3 0 -9.8 0) hk)
    (new PhysicsAggregate box1 (.-BOX PhysicsShapeType) #js {:mass 1} scene)
    (new PhysicsAggregate box2 (.-BOX PhysicsShapeType) #js {:mass 1} scene)
    (new PhysicsAggregate ground (.-BOX PhysicsShapeType) #js {:mass 0} scene)
    (js/console.log "aaaaaaa" scene)
    (.runRenderLoop engine (fn [] (when scene (.render scene))))))

(-> (HavokPhysics #js {:locateFile (fn [_] "havok.wasm")})
    (.then app))
