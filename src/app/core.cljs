(ns app.core
  (:require
   ["@babylonjs/core/Cameras/freeCamera.js" :refer [FreeCamera]]
   ["@babylonjs/core/Engines/engine.js" :refer [Engine]]
   ["@babylonjs/core/Loading/loadingScreen"]
   ["@babylonjs/core/Loading/sceneLoader.js" :refer [AppendSceneAsync]]
   ["@babylonjs/core/Materials/standardMaterial"]
   ["@babylonjs/core/Maths/math.vector.js" :refer [Vector3]]
   ["@babylonjs/core/Physics/physicsEngineComponent.js"]
   ["@babylonjs/core/Physics/v2/IPhysicsEnginePlugin.js" :refer [PhysicsShapeType]]
   ["@babylonjs/core/Physics/v2/physicsAggregate.js" :refer [PhysicsAggregate]]
   ["@babylonjs/core/Physics/v2/Plugins/havokPlugin.js" :refer [HavokPlugin]]
   ["@babylonjs/core/scene.js" :refer [Scene]]
   ["@babylonjs/havok$default" :as HavokPhysics]
   ["@babylonjs/loaders/dynamic" :refer [registerBuiltInLoaders]]))

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
        camera (new FreeCamera "camera1" (new Vector3 0 5 -10) scene)]

    (-> (AppendSceneAsync "assets/test-scene.glb" scene)
        (.then (fn []
                 (.setTarget camera (. Vector3 Zero))
                 (.attachControl camera canvas true)
                 (.enablePhysics scene (new Vector3 0 -9.8 0) hk)

                 (doseq [^js mesh (.-meshes scene)]
                   (when-let [metadata (.-metadata mesh)]
                     (let [extras (-> metadata .-gltf .-extras)
                           type (.-collider_type extras)
                           shape (.-collider_shape extras)]
                       (case [type shape]
                         ["static" "box"]
                         (new PhysicsAggregate mesh (.-BOX PhysicsShapeType) #js {:mass 0} scene)
                         ["dynamic" "box"]
                         (new PhysicsAggregate mesh (.-BOX PhysicsShapeType) #js {:mass 1} scene)
                         ["dynamic" "sphere"]
                         (new PhysicsAggregate mesh (.-SPHERE PhysicsShapeType) #js {:mass 1 :restitution 0.5} scene)))))

                 (.runRenderLoop engine (fn [] (when scene (.render scene)))))))))

(registerBuiltInLoaders)

(-> (HavokPhysics #js {:locateFile (fn [_] "havok.wasm")})
    (.then app))
