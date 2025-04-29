import * as i0 from "@babylonjs/core/Lights";
import * as i1 from "@babylonjs/core/Physics/v2/Plugins/havokPlugin";
import * as i2 from "@babylonjs/havok";
import "@babylonjs/core/Loading/loadingScreen";
import "@babylonjs/core/Physics/physicsEngineComponent.js";
import * as i3 from "@babylonjs/core/Physics/v2/IPhysicsEnginePlugin";
import * as i4 from "@babylonjs/core/Physics/v2/physicsAggregate";
import * as i5 from "@babylonjs/loaders/dynamic";
import * as i6 from "@babylonjs/core/Maths/math.vector";
import * as i7 from "@babylonjs/core/Loading/sceneLoader";
import "@babylonjs/core/Materials/standardMaterial";
import * as i8 from "@babylonjs/core/scene";
import * as i9 from "@babylonjs/core/Engines/engine";
import * as i10 from "@babylonjs/core/Cameras/freeCamera";

const ALL = {};

globalThis.shadow$bridge = function(name) {
  const ret = ALL[name];
  if (ret == undefined) {
    throw new Error("Dependency: " + name + " not provided by external JS!");
  } else {
    return ret;
  }
};

ALL["@babylonjs/core/Lights"] = {
  HemisphericLight: i0.HemisphericLight
};

ALL["@babylonjs/core/Physics/v2/Plugins/havokPlugin"] = {
  HavokPlugin: i1.HavokPlugin
};

ALL["@babylonjs/havok"] = {
  default: i2.default
};

ALL["@babylonjs/core/Loading/loadingScreen"] = {

};

ALL["@babylonjs/core/Physics/physicsEngineComponent.js"] = {

};

ALL["@babylonjs/core/Physics/v2/IPhysicsEnginePlugin"] = {
  PhysicsShapeType: i3.PhysicsShapeType
};

ALL["@babylonjs/core/Physics/v2/physicsAggregate"] = {
  PhysicsAggregate: i4.PhysicsAggregate
};

ALL["@babylonjs/loaders/dynamic"] = {
  registerBuiltInLoaders: i5.registerBuiltInLoaders
};

ALL["@babylonjs/core/Maths/math.vector"] = {
  Vector3: i6.Vector3
};

ALL["@babylonjs/core/Loading/sceneLoader"] = {
  AppendSceneAsync: i7.AppendSceneAsync
};

ALL["@babylonjs/core/Materials/standardMaterial"] = {

};

ALL["@babylonjs/core/scene"] = {
  Scene: i8.Scene
};

ALL["@babylonjs/core/Engines/engine"] = {
  Engine: i9.Engine
};

ALL["@babylonjs/core/Cameras/freeCamera"] = {
  FreeCamera: i10.FreeCamera
};
