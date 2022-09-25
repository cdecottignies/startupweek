/**
 * Can find a node in a tree by it's properties code or id.
 * if tree or (params.code and params.id) are null or undefined, it returns null
 * @param {Array<Object>} tree the tree to look into. It in unmodified by the function
 * @param {Object} params an object containing different params:
 *        {Integer} code | The nomenclature code of the node searched
 *        {String}  id | The nomenclature id of the node searched.
 *        {Boolean} withParents | Should the parents be returned as well ?
 *        {Boolean} withChildren | if the code is not final, should the children be returned as well ?
 *        {Boolean} closestParent | if we could not find the requested code, should we return the closest parent ?
 *        {Boolean} uniqueIds | usefull when we want display multiple time the same tree part as ids must be unique. Will append a unique string to end of id to make it unique
 *        {Boolean} incrementalIds | not mandatory, enabled the function to be faster by knowing the tree is sorted so that it can search smartly in it.
 * @returns null if no code or id provided / found. Tree with or without parents otherwise
 */
export function findNodeByCodeOrId (tree, params) {
  // the bellow indicates us if we have an id, or if we should try to find the code or closest parent
  const codeMode = params.code
  // If we have no ID nor Code, we return null
  if ((!codeMode && !params.id) || !tree) return null
  for (let i = 0; i < tree.length; i++) {
    const node = JSON.parse(JSON.stringify(tree[i]));
    if (params.uniqueIds) {
      node.id = node.id + "-" + Math.random();
    }
    // The bellow code is for performance reasons. We try to see if it is worth exploring children or not.
    // Can be safely removed for easier comprehension and/or troubleshooting
    // It only works if id and codes are incremental as we progress in the tree
    let shouldExploreChildren = false
    if (params.incrementalIds) {
      if (codeMode) {
        shouldExploreChildren = !node.code || node.code === params.code.substring(0, node.code.length)
      } else {
        if (i + 1 === tree.length) {
          shouldExploreChildren = true;
        } else {
          const nextNodeId = parseInt(tree[i + 1].id.toString().split('-')[0]);
          const nodeId = parseInt(params.id.toString().split('-')[0]);
          shouldExploreChildren = nextNodeId > nodeId
        }
      }
    } else {
      shouldExploreChildren = true
    }
    // end of optional code
    if ((codeMode && node.code === params.code) || (!codeMode && node.id === params.id)) {
      if (!params.withChildren) node.children = [];
      return node;
    } else if (shouldExploreChildren) {
      const result = findNodeByCodeOrId(node.children, params);
      if (result) {
        if (params.withParents) {
          const rtn = node;
          rtn.children = [result];
          return rtn;
        } else {
          return result;
        }
      } else { // Dead End, it looks like the code requested doesn't exist in our tree
        // But if it was requested to return closest parent and if at least the beginning is similar, we return this node.
        if (params.closestParent && codeMode && node.code && node.code === params.code.substring(0, node.code.length)) {
          node.children = [];
          return node;
        }
      }
    }
  }
}
