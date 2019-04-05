
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
		paletteRoot.add(createObjects1Group());
		paletteRoot.add(createConnections2Group());
	}

	/**
	* Creates "Objects" palette tool group
	* @generated
	*/
	private PaletteContainer createObjects1Group() {
		PaletteDrawer paletteContainer = new PaletteDrawer(Messages.Objects1Group_title);
		paletteContainer.setId("createObjects1Group"); //$NON-NLS-1$
		paletteContainer.add(createCommitmentTask1CreationTool());
		paletteContainer.add(createConfiguration2CreationTool());
		paletteContainer.add(createCsvSink3CreationTool());
		paletteContainer.add(createCsvSource4CreationTool());
		paletteContainer.add(createField5CreationTool());
		paletteContainer.add(createOpinionatedTask6CreationTool());
		paletteContainer.add(createQueue7CreationTool());
		paletteContainer.add(createSink8CreationTool());
		paletteContainer.add(createSource9CreationTool());
		paletteContainer.add(createTask10CreationTool());
		paletteContainer.add(createTopic11CreationTool());
		paletteContainer.add(createType12CreationTool());
		return paletteContainer;
	}

	/**
	* Creates "Connections" palette tool group
	* @generated
	*/
	private PaletteContainer createConnections2Group() {
		PaletteDrawer paletteContainer = new PaletteDrawer(Messages.Connections2Group_title);
		paletteContainer.setId("createConnections2Group"); //$NON-NLS-1$
		paletteContainer.add(createExtending1CreationTool());
		paletteContainer.add(createInput2CreationTool());
		paletteContainer.add(createOutput3CreationTool());
		paletteContainer.add(createType4CreationTool());
		return paletteContainer;
	}

	/**
	* @generated
	*/
	private ToolEntry createCommitmentTask1CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.CommitmentTask1CreationTool_title,
				Messages.CommitmentTask1CreationTool_desc,
				Collections.singletonList(CrossflowElementTypes.CommitmentTask_2007));
		entry.setId("createCommitmentTask1CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.CommitmentTask_2007));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createConfiguration2CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.Configuration2CreationTool_title,
				Messages.Configuration2CreationTool_desc,
				Collections.singletonList(CrossflowElementTypes.Configuration_2009));
		entry.setId("createConfiguration2CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.Configuration_2009));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createCsvSink3CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.CsvSink3CreationTool_title,
				Messages.CsvSink3CreationTool_desc, Collections.singletonList(CrossflowElementTypes.CsvSink_2002));
		entry.setId("createCsvSink3CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.CsvSink_2002));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createCsvSource4CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.CsvSource4CreationTool_title,
				Messages.CsvSource4CreationTool_desc, Collections.singletonList(CrossflowElementTypes.CsvSource_2001));
		entry.setId("createCsvSource4CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.CsvSource_2001));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createField5CreationTool() {
		ArrayList<IElementType> types = new ArrayList<IElementType>(2);
		types.add(CrossflowElementTypes.Field_3001);
		types.add(CrossflowElementTypes.Field_2012);
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.Field5CreationTool_title,
				Messages.Field5CreationTool_desc, types);
		entry.setId("createField5CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.Field_3001));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createOpinionatedTask6CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.OpinionatedTask6CreationTool_title,
				Messages.OpinionatedTask6CreationTool_desc,
				Collections.singletonList(CrossflowElementTypes.OpinionatedTask_2008));
		entry.setId("createOpinionatedTask6CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.OpinionatedTask_2008));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createQueue7CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.Queue7CreationTool_title,
				Messages.Queue7CreationTool_desc, Collections.singletonList(CrossflowElementTypes.Queue_2004));
		entry.setId("createQueue7CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.Queue_2004));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createSink8CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.Sink8CreationTool_title,
				Messages.Sink8CreationTool_desc, Collections.singletonList(CrossflowElementTypes.Sink_2006));
		entry.setId("createSink8CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.Sink_2006));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createSource9CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.Source9CreationTool_title,
				Messages.Source9CreationTool_desc, Collections.singletonList(CrossflowElementTypes.Source_2005));
		entry.setId("createSource9CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.Source_2005));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createTask10CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.Task10CreationTool_title,
				Messages.Task10CreationTool_desc, Collections.singletonList(CrossflowElementTypes.Task_2010));
		entry.setId("createTask10CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.Task_2010));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createTopic11CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.Topic11CreationTool_title,
				Messages.Topic11CreationTool_desc, Collections.singletonList(CrossflowElementTypes.Topic_2003));
		entry.setId("createTopic11CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.Topic_2003));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createType12CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.Type12CreationTool_title,
				Messages.Type12CreationTool_desc, Collections.singletonList(CrossflowElementTypes.Type_2011));
		entry.setId("createType12CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.Type_2011));
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
	private ToolEntry createInput2CreationTool() {
		DefaultLinkToolEntry entry = new DefaultLinkToolEntry(Messages.Input2CreationTool_title,
				Messages.Input2CreationTool_desc, Collections.singletonList(CrossflowElementTypes.TaskInput_4002));
		entry.setId("createInput2CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.TaskInput_4002));
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
