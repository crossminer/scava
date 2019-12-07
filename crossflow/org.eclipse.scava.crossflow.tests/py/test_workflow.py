import unittest

from crossflow.calculator.calculation import Calculation
from crossflow.calculator.calculation_result import WorkerLang, CalculationResult
from crossflow.calculator.calculator_workflow import CalculatorWorkflow
from crossflow.serialization import Serializer


class TestWorkflow(unittest.TestCase):
    def test_register_custom_serialization_types_should_register_enum_generated_from_enumfield(
        self,
    ):
        """
        Asserts that Enumerates generated from EnumField are correctly registered
        with the generated Workflow
        """
        self.assertIn(WorkerLang, CalculatorWorkflow().serializer.registered_types)

    def test_register_custom_serialization_types_should_register_type_generated_from_type(
        self,
    ):
        """
        Asserts that Types generated from Type are correctly registered with
        the generated Workflow
        """
        self.assertIn(Calculation, CalculatorWorkflow().serializer.registered_types)
        self.assertIn(
            CalculationResult, CalculatorWorkflow().serializer.registered_types
        )
