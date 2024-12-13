/*
 *  Copyright (c) 2024 Metaform Systems, Inc.
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Apache License, Version 2.0 which is available at
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 *  SPDX-License-Identifier: Apache-2.0
 *
 *  Contributors:
 *       Metaform Systems, Inc. - initial API and implementation
 *
 */

package org.eclipse.dsp.generation.jsom;

/**
 * Schema for testing.
 */
public interface TestSchema {

    String TEST_SCHEMA = """
            {
              "$schema": "https://json-schema.org/draft/2019-09/schema",
              "title": "PolicySchema",
              "type": "object",
              "allOf": [
                {
                  "$ref": "#/definitions/Policy"
                }
              ],
              "$id": "https://w3id.org/dspace/2024/1/negotiation/contract-schema.json",
              "definitions": {
                "Policy": {
                  "oneOf": [
                    {
                      "$ref": "#/definitions/MessageOffer"
                    },
                    {
                      "$ref": "#/definitions/Offer"
                    },
                    {
                      "$ref": "#/definitions/Agreement"
                    }
                  ]
                },
                "PolicyClass": {
                  "type": "object",
                  "properties": {
                    "@id": {
                      "type": "string"
                    },
                    "profile": {
                      "oneOf": [
                        {
                          "type": "array",
                          "items": "string"
                        },
                        {
                          "type": "string"
                        }
                      ]
                    },
                    "permission": {
                      "type": "array",
                      "items": {
                        "$ref": "#/definitions/Permission"
                      },
                      "minItems": 1
                    },
                    "obligation": {
                      "type": "array",
                      "items": {
                        "$ref": "#/definitions/Duty"
                      },
                      "minItems": 1
                    }
                  },
                  "required": [
                    "@id"
                  ]
                },
                "MessageOffer": {
                  "type": "object",
                  "allOf": [
                    {
                      "$ref": "#/definitions/PolicyClass"
                    },
                    {
                      "properties": {
                        "@type": {
                          "type": "string",
                          "const": "Offer"
                        }
                      }
                    },
                    {
                      "anyOf": [
                        {
                          "required": [
                            "permission"
                          ]
                        },
                        {
                          "required": [
                            "prohibition"
                          ]
                        }
                      ]
                    }
                  ],
                  "required": [
                    "@type"
                  ]
                },
                "Offer": {
                  "type": "object",
                  "allOf": [
                    {
                      "$ref": "#/definitions/MessageOffer"
                    }
                  ],
                  "not": {
                    "required": [
                      "target"
                    ]
                  }
                },
                "Agreement": {
                  "type": "object",
                  "allOf": [
                    {
                      "$ref": "#/definitions/PolicyClass"
                    },
                    {
                      "properties": {
                        "@type": {
                          "type": "string",
                          "const": "Agreement"
                        },
                        "target": {
                          "type": "string"
                        },
                        "assigner": {
                          "$comment" :"The dataset provider",
                          "type": "string"
                        },
                        "assignee": {
                          "$comment" :"The dataset consumer",
                          "type": "string"
                        },
                        "timestamp": {
                          "type": "string",
                          "pattern": "-?([1-9][0-9]{3,}|0[0-9]{3})-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])T(([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9](\\\\.[0-9]+)?|(24:00:00(\\\\.0+)?))(Z|(\\\\+|-)((0[0-9]|1[0-3]):[0-5][0-9]|14:00))?"
                        }
                      }
                    },
                    {
                      "anyOf": [
                        {
                          "required": [
                            "permission"
                          ]
                        },
                        {
                          "required": [
                            "prohibition"
                          ]
                        }
                      ]
                    }
                  ],
                  "required": [
                    "@type",
                    "target",
                    "assignee",
                    "assigner"
                  ]
                },
                "Permission": {
                  "type": "object",
                  "properties": {
                    "action": {
                      "$ref": "#/definitions/Action"
                    },
                    "constraint": {
                      "type": "array",
                      "items": {
                        "$ref": "#/definitions/Constraint"
                      }
                    },
                    "duty": {
                      "$ref": "#/definitions/Duty"
                    }
                  },
                  "required": [
                    "action"
                  ]
                },
                "Duty": {
                  "type": "object",
                  "allOf": [
                    {
                      "properties": {
                        "action": {
                          "$ref": "#/definitions/Action"
                        },
                        "constraint": {
                          "type": "array",
                          "items": {
                            "$ref": "#/definitions/Constraint"
                          }
                        }
                      },
                      "required": [
                        "action"
                      ]
                    }
                  ]
                },
                "Action": {
                  "type": "string"
                },
                "Constraint": {
                  "type": "object",
                  "oneOf": [
                    {
                      "$ref": "#/definitions/LogicalConstraint"
                    },
                    {
                      "$ref": "#/definitions/AtomicConstraint"
                    }
                  ]
                },
                "LogicalConstraint": {
                  "type": "object",
                  "properties": {
                    "and": {
                      "type": "array",
                      "items": "object"
                    },
                    "andSequence": {
                      "type": "array",
                      "items": "object"
                    },
                    "or": {
                      "type": "array",
                      "items": {
                        "oneOf": [
                          {
                            "$ref": "#/definitions/LogicalConstraint"
                          },
                          {
                            "$ref": "#/definitions/AtomicConstraint"
                          }
                        ]
                      }
                    },
                    "xone": {
                      "type": "array",
                      "items": "object"
                    }
                  },
                  "oneOf": [
                    {
                      "required": [
                        "and"
                      ]
                    },
                    {
                      "required": [
                        "andSequence"
                      ]
                    },
                    {
                      "required": [
                        "or"
                      ]
                    },
                    {
                      "required": [
                        "xone"
                      ]
                    }
                  ]
                },
                "AtomicConstraint": {
                  "type": "object",
                  "properties": {
                    "rightOperand": {
                      "$ref": "#/definitions/RightOperand"
                    },
                    "leftOperand": {
                      "$ref": "#/definitions/LeftOperand"
                    },
                    "operator": {
                      "$ref": "#/definitions/Operator"
                    }
                  },
                  "required": [
                    "rightOperand",
                    "operator",
                    "leftOperand"
                  ]
                },
                "Operator": {
                  "type": "string",
                  "enum": [
                    "eq",
                    "gt",
                    "gteq",
                    "lteq",
                    "hasPart",
                    "isA",
                    "isAllOf",
                    "isAnyOf",
                    "isNoneOf",
                    "isPartOf",
                    "lt",
                    "term-lteq",
                    "neq"
                  ]
                },
                "RightOperand": {
                  "oneOf": [
                    {
                      "type": "string"
                    },
                    {
                      "type": "object"
                    },
                    {
                      "type": "array"
                    }
                  ]
                },
                "LeftOperand": {
                  "type": "string"
                },
                "Reference": {
                  "type": "object",
                  "properties": {
                    "@id": {
                      "type": "string"
                    }
                  },
                  "required": [
                    "@id"
                  ]
                }
              }
            }
            """;
}
