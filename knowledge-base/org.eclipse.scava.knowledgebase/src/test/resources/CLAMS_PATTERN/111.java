{
    final Rule rule;
    final KnowledgePackage knowledgePackage;
    final String EXPECTED_PACKAGE_NAME;
    final String EXPECTED_RULE_NAME;
    assertThat(knowledgePackage.getName()).as( " a string " ).isEqualTo(EXPECTED_PACKAGE_NAME);
    final Collection<Rule> rules = knowledgePackage.getRules();
    assertThat(rule.getName()).as( " a string " ).isEqualTo(EXPECTED_RULE_NAME);
    // Do something with rules

}    assertThat(knowledgePackage.getName()).as( "  " a string "  " ).isEqualTo(EXPECTED_PACKAGE_NAME);    assertThat(rule.getName()).as( "  " a string "  " ).isEqualTo(EXPECTED_RULE_NAME);    assertThat(knowledgePackage.getName()).as( "  " a string "  " ).isEqualTo(EXPECTED_PACKAGE_NAME);    assertThat(rule.getName()).as( "  " a string "  " ).isEqualTo(EXPECTED_RULE_NAME);}    assertThat(knowledgePackage.getName()).as( "  "  " a string "  "  " ).isEqualTo(EXPECTED_PACKAGE_NAME);    assertThat(rule.getName()).as( "  "  " a string "  "  " ).isEqualTo(EXPECTED_RULE_NAME);
