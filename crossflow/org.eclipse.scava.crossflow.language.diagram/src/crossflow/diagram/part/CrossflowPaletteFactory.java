
/*
 * 
 */
package crossflow.diagram.part;

import java.util.ArrayList;
import java.util.Collections;

import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.tooling.runtime.part.DefaultLinkToolEntry;
import org.eclipse.gmf.tooling.runtime.part.DefaultNodeToolEntry;

import crossflow.diagram.providers.CrossflowElementTypes;

/**
 * @generated
 */
public class CrossflowPaletteFactory {

	/**
	* @generated
	*/
	public void fillPalette(PaletteRoot paletteRoot) {
		paletteRoot.add(createSourcesandSinks1Group());
		paletteRoot.add(createTasks2Group());
		paletteRoot.add(createStreams3Group());
		paletteRoot.add(createDatatypes4Group());
		paletteRoot.add(createOther5Group());
		paletteRoot.add(createConnections6Group());
	}

	/**
	* Creates "Sources and Sinks" palette tool group
	* @generated
	*/
	private PaletteContainer createSourcesandSinks1Group() {
		PaletteDrawer paletteContainer = new PaletteDrawer(Messages.SourcesandSinks1Group_title);
		paletteContainer.setId("createSourcesandSinks1Group"); //$NON-NLS-1$
		paletteContainer.add(createSource1CreationTool());
		paletteContainer.add(createSink2CreationTool());
		paletteContainer.add(createCsvSource3CreationTool());
		paletteContainer.add(createCsvSink4CreationTool());
		return paletteContainer;
	}

	/**
	* Creates "Tasks" palette tool group
	* @generated
	*/
	private PaletteContainer createTasks2Group() {
		PaletteDrawer paletteContainer = new PaletteDrawer(Messages.Tasks2Group_title);
		paletteContainer.setId("createTasks2Group"); //$NON-NLS-1$
		paletteContainer.add(createTask1CreationTool());
		paletteContainer.add(createCommitmentTask2CreationTool());
		paletteContainer.add(createOpinionatedTask3CreationTool());
		paletteContainer.add(createScriptedTask4CreationTool());
		paletteContainer.add(createReusableComponent5CreationTool());
		return paletteContainer;
	}

	/**
	* Creates "Streams" palette tool group
	* @generated
	*/
	private PaletteContainer createStreams3Group() {
		PaletteDrawer paletteContainer = new PaletteDrawer(Messages.Streams3Group_title);
		paletteContainer.setId("createStreams3Group"); //$NON-NLS-1$
		paletteContainer.add(createQueue1CreationTool());
		paletteContainer.add(createTopic2CreationTool());
		return paletteContainer;
	}

	/**
	* Creates "Datatypes" palette tool group
	* @generated
	*/
	private PaletteContainer createDatatypes4Group() {
		PaletteDrawer paletteContainer = new PaletteDrawer(Messages.Datatypes4Group_title);
		paletteContainer.setId("createDatatypes4Group"); //$NON-NLS-1$
		paletteContainer.add(createType1CreationTool());
		paletteContainer.add(createDataField2CreationTool());
		paletteContainer.add(createEnumField3CreationTool());
		return paletteContainer;
	}

	/**
	* Creates "Other" palette tool group
	* @generated
	*/
	private PaletteContainer createOther5Group() {
		PaletteDrawer paletteContainer = new PaletteDrawer(Messages.Other5Group_title);
		paletteContainer.setId("createOther5Group"); //$NON-NLS-1$
		paletteContainer.add(createLanguage1CreationTool());
		paletteContainer.add(createParameter2CreationTool());
		paletteContainer.add(createSerializer3CreationTool());
		return paletteContainer;
	}

	/**
	* Creates "Connections" palette tool group
	* @generated
	*/
	private PaletteContainer createConnections6Group() {
		PaletteDrawer paletteContainer = new PaletteDrawer(Messages.Connections6Group_title);
		paletteContainer.setId("createConnections6Group"); //$NON-NLS-1$
		paletteContainer.add(createExtending1CreationTool());
		paletteContainer.add(createInputOf2CreationTool());
		paletteContainer.add(createOutput3CreationTool());
		paletteContainer.add(createType4CreationTool());
		return paletteContainer;
	}

	/**
	* @generated
	*/
	private ToolEntry createSource1CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.Source1CreationTool_title,
				Messages.Source1CreationTool_desc, Collections.singletonList(CrossflowElementTypes.Source_2005));
		entry.setId("createSource1CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.Source_2005));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createSink2CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.Sink2CreationTool_title,
				Messages.Sink2CreationTool_desc, Collections.singletonList(CrossflowElementTypes.Sink_2006));
		entry.setId("createSink2CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.Sink_2006));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createCsvSource3CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.CsvSource3CreationTool_title,
				Messages.CsvSource3CreationTool_desc, Collections.singletonList(CrossflowElementTypes.CsvSource_2001));
		entry.setId("createCsvSource3CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.CsvSource_2001));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createCsvSink4CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.CsvSink4CreationTool_title,
				Messages.CsvSink4CreationTool_desc, Collections.singletonList(CrossflowElementTypes.CsvSink_2002));
		entry.setId("createCsvSink4CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.CsvSink_2002));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createTask1CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.Task1CreationTool_title,
				Messages.Task1CreationTool_desc, Collections.singletonList(CrossflowElementTypes.Task_2010));
		entry.setId("createTask1CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.Task_2010));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createCommitmentTask2CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.CommitmentTask2CreationTool_title,
				Messages.CommitmentTask2CreationTool_desc,
				Collections.singletonList(CrossflowElementTypes.CommitmentTask_2007));
		entry.setId("createCommitmentTask2CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.CommitmentTask_2007));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createOpinionatedTask3CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.OpinionatedTask3CreationTool_title,
				Messages.OpinionatedTask3CreationTool_desc,
				Collections.singletonList(CrossflowElementTypes.OpinionatedTask_2008));
		entry.setId("createOpinionatedTask3CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.OpinionatedTask_2008));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createScriptedTask4CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.ScriptedTask4CreationTool_title,
				Messages.ScriptedTask4CreationTool_desc,
				Collections.singletonList(CrossflowElementTypes.ScriptedTask_2015));
		entry.setId("createScriptedTask4CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.ScriptedTask_2015));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createReusableComponent5CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.ReusableComponent5CreationTool_title,
				Messages.ReusableComponent5CreationTool_desc,
				Collections.singletonList(CrossflowElementTypes.ReusableComponent_2017));
		entry.setId("createReusableComponent5CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.ReusableComponent_2017));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createQueue1CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.Queue1CreationTool_title,
				Messages.Queue1CreationTool_desc, Collections.singletonList(CrossflowElementTypes.Queue_2004));
		entry.setId("createQueue1CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.Queue_2004));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createTopic2CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.Topic2CreationTool_title,
				Messages.Topic2CreationTool_desc, Collections.singletonList(CrossflowElementTypes.Topic_2003));
		entry.setId("createTopic2CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.Topic_2003));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createType1CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.Type1CreationTool_title,
				Messages.Type1CreationTool_desc, Collections.singletonList(CrossflowElementTypes.Type_2011));
		entry.setId("createType1CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.Type_2011));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createDataField2CreationTool() {
		ArrayList<IElementType> types = new ArrayList<IElementType>(3);
		types.add(CrossflowElementTypes.DataField_3006);
		types.add(CrossflowElementTypes.DataField_2019);
		types.add(CrossflowElementTypes.DataField_3008);
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.DataField2CreationTool_title,
				Messages.DataField2CreationTool_desc, types);
		entry.setId("createDataField2CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.DataField_3006));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createEnumField3CreationTool() {
		ArrayList<IElementType> types = new ArrayList<IElementType>(3);
		types.add(CrossflowElementTypes.EnumField_3007);
		types.add(CrossflowElementTypes.EnumField_2020);
		types.add(CrossflowElementTypes.EnumField_3009);
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.EnumField3CreationTool_title,
				Messages.EnumField3CreationTool_desc, types);
		entry.setId("createEnumField3CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.EnumField_3007));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createLanguage1CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.Language1CreationTool_title,
				Messages.Language1CreationTool_desc, Collections.singletonList(CrossflowElementTypes.Language_2013));
		entry.setId("createLanguage1CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.Language_2013));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createParameter2CreationTool() {
		ArrayList<IElementType> types = new ArrayList<IElementType>(2);
		types.add(CrossflowElementTypes.Parameter_3002);
		types.add(CrossflowElementTypes.Parameter_3005);
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.Parameter2CreationTool_title,
				Messages.Parameter2CreationTool_desc, types);
		entry.setId("createParameter2CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.Parameter_3002));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createSerializer3CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.Serializer3CreationTool_title,
				Messages.Serializer3CreationTool_desc,
				Collections.singletonList(CrossflowElementTypes.Serializer_2018));
		entry.setId("createSerializer3CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.Serializer_2018));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createExtending1CreationTool() {
		DefaultLinkToolEntry entry = new DefaultLinkToolEntry(Messages.Extending1CreationTool_title,
				Messages.Extending1CreationTool_desc,
				Collections.singletonList(CrossflowElementTypes.TypeExtending_4004));
		entry.setId("createExtending1CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.TypeExtending_4004));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createInputOf2CreationTool() {
		DefaultLinkToolEntry entry = new DefaultLinkToolEntry(Messages.InputOf2CreationTool_title,
				Messages.InputOf2CreationTool_desc,
				Collections.singletonList(CrossflowElementTypes.StreamInputOf_4005));
		entry.setId("createInputOf2CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.StreamInputOf_4005));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createOutput3CreationTool() {
		DefaultLinkToolEntry entry = new DefaultLinkToolEntry(Messages.Output3CreationTool_title,
				Messages.Output3CreationTool_desc, Collections.singletonList(CrossflowElementTypes.TaskOutput_4003));
		entry.setId("createOutput3CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.TaskOutput_4003));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createType4CreationTool() {
		DefaultLinkToolEntry entry = new DefaultLinkToolEntry(Messages.Type4CreationTool_title,
				Messages.Type4CreationTool_desc, Collections.singletonList(CrossflowElementTypes.StreamType_4001));
		entry.setId("createType4CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.StreamType_4001));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

}
