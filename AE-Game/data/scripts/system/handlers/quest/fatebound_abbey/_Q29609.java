package quest.fatebound_abbey;

import org.typezero.gameserver.model.DialogAction;
import org.typezero.gameserver.model.gameobjects.player.Player;
import org.typezero.gameserver.questEngine.handlers.QuestHandler;
import org.typezero.gameserver.questEngine.model.QuestEnv;
import org.typezero.gameserver.questEngine.model.QuestState;
import org.typezero.gameserver.questEngine.model.QuestStatus;

/**
 * @author Romanz
 *
 */
public class _Q29609 extends QuestHandler
{
	private final static int questId = 29609;

	public _Q29609()
	{
		super(questId);
	}

	@Override
	public void register()
	{
		qe.registerQuestNpc(804662).addOnQuestStart(questId);
		qe.registerQuestNpc(804662).addOnTalkEvent(questId);
		qe.registerQuestNpc(799225).addOnTalkEvent(questId);
		qe.registerQuestNpc(215989).addOnKillEvent(questId);
		qe.registerQuestNpc(215931).addOnKillEvent(questId);
	}

	@Override
	public boolean onDialogEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		DialogAction dialog = env.getDialog();
		int targetId = env.getTargetId();

		if (qs == null || qs.getStatus() == QuestStatus.NONE || qs.canRepeat()) {
			if (targetId == 804662) {
				if (dialog == DialogAction.QUEST_SELECT)
					return sendQuestDialog(env, 4762);
				else
					return sendQuestStartDialog(env);
			}
		}
		else if (qs.getStatus() == QuestStatus.REWARD)
		{
			if (targetId == 799225)
			{
				if(env.getDialogId() == DialogAction.USE_OBJECT.id())
					return sendQuestDialog(env, 10002);
				else if(env.getDialogId() == DialogAction.SELECT_QUEST_REWARD.id())
					return sendQuestDialog(env, 5);
				else
					return sendQuestEndDialog(env);
			}
		}
		return false;
	}

	@Override
	public boolean onKillEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if(qs == null || qs.getStatus() != QuestStatus.START)
			return false;

		int targetId = env.getTargetId();

		switch(targetId)
		{
				case 215989:
				case 215931:
					if (qs.getQuestVarById(0) == 0)
					{
						if(qs.getQuestVarById(1) <= 9)
						{
							qs.setQuestVarById(1, qs.getQuestVarById(1) + 1);
							updateQuestStatus(env);
						}
						if(qs.getQuestVarById(1) == 10)
						{
							qs.setStatus(QuestStatus.REWARD);
							qs.setQuestVarById(0, 1);
							updateQuestStatus(env);
						}
					}
					break;
			}
		return false;
	}
}
