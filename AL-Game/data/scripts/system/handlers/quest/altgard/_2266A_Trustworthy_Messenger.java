/*
 * This file is part of Encom.
 *
 *  Encom is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Encom is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser Public License
 *  along with Encom.  If not, see <http://www.gnu.org/licenses/>.
 */
package quest.altgard;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestDialog;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

/****/
/** Author (Encom)
/****/

public class _2266A_Trustworthy_Messenger extends QuestHandler {

	private final static int questId = 2266;
	public _2266A_Trustworthy_Messenger() {
		super(questId);
	}
	
	@Override
	public void register() {
		qe.registerQuestNpc(203558).addOnQuestStart(questId);
		qe.registerQuestNpc(203558).addOnTalkEvent(questId);
		qe.registerQuestNpc(203655).addOnTalkEvent(questId);
		qe.registerQuestNpc(203654).addOnTalkEvent(questId);
	}
	
	@Override
	public boolean onDialogEvent(QuestEnv env) {
		int targetId = 0;
		final Player player = env.getPlayer();
		final QuestState qs = player.getQuestStateList().getQuestState(questId);
		QuestDialog dialog = env.getDialog();
		if (env.getVisibleObject() instanceof Npc) {
			targetId = ((Npc) env.getVisibleObject()).getNpcId();
		} if (qs == null || qs.getStatus() == QuestStatus.NONE) {
			if (targetId == 203558) {
				if (dialog == QuestDialog.START_DIALOG) {
					return sendQuestDialog(env, 1011);
				} else if (dialog == QuestDialog.ACCEPT_QUEST) {
					if (!giveQuestItem(env, 182203244, 1)) {
						return true;
					}
					return sendQuestStartDialog(env);
				} else {
					return sendQuestStartDialog(env);
				}
			}
		} else if (qs.getStatus() == QuestStatus.START) {
			int var = qs.getQuestVarById(0);
			if (targetId == 203655) {
				switch (dialog) {
					case START_DIALOG: {
						if (var == 0) {
							return sendQuestDialog(env, 1352);
						}
					} case STEP_TO_1: {
						if (var == 0) {
							qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
							updateQuestStatus(env);
					        return closeDialogWindow(env);
						}
					}
				}
			} if (targetId == 203654) {
				switch (dialog) {
					case START_DIALOG: {
						if (var == 1) {
							qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
							return sendQuestDialog(env, 2375);
						}
					} case SELECT_REWARD: {
						if (var == 2) {
							qs.setStatus(QuestStatus.REWARD);
							updateQuestStatus(env);
							removeQuestItem(env, 182203244, 1);
							return sendQuestEndDialog(env);
						}
					}
				}
			}
		} else if (qs == null || qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 203654)
			return sendQuestEndDialog(env);
		}
		return false;
	}
}